def terraformWithRollack(message) {
    try {
        env.TF_IN_AUTOMATION = true
        echo "executing terraform ${message} "
        sh "terraform ${message}"
        //sh "pwd"
        if (fileExists("terraform.values")) {

            echo "terraform value file exists"

        }

    }
        catch (Exception error ) {

            echo "Failure during terraform ${message} "
            echo "......................Initiating a automatic rollback ......................"

            if (fileExists("terraform.values")) {

                echo "Inside exception terraform value file exists"
                sh "terraform plan -var-file=terraform.values -destroy -out destroy.plan"
                sh "terraform apply destroy.plan"


            } else {
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