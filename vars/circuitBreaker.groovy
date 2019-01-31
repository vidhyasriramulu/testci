def terraformWithRollack(message) {
    try {
        echo "executing terraform ${message} "
        env.TF_IN_AUTOMATION = true
        sh "terraform ${message}"

    }
        catch (Exception error ) {

            echo "Failure during terraform ${message} "
            echo "......................Initiating a automatic rollback ......................"

            if (fileExists("terraform.values")) {

                if (fileExists("terraform.tfstate")) {
                    echo "Inside exception terraform value file exists"
                    env.TF_IN_AUTOMATION = true
                    sh "terraform plan -var-file=terraform.values -destroy -out destroy.plan -auto-approve"
                    env.TF_IN_AUTOMATION = true
                    sh "terraform apply destroy.plan "

                }

            }


             else {
                echo "Inside exception terraform value file exists"
                sh "terraform plan -destroy -out destroy.plan"
                sh "terraform apply destroy.plan"
            }


            throw error
        }
}

def warning(message) {
    echo "WARNING: ${message}"
}

def call(int buildNumber) {
    if (buildNumber % 2 == 0) {
        pipeline {
            agent any
            stages {
                stage('Even Stage') {
                    steps {
                        echo "The build number is even"
                    }
                }
            }
        }
    } else {
        pipeline {
            agent any
            stages {
                stage('Odd Stage') {
                    steps {
                        echo "The build number is odd"
                    }
                }
            }
        }
    }
}