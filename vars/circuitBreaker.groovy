def terraformWithRollack(message) {
    try {
        env.TF_IN_AUTOMATION = true
        sh "pwd"
        sh "terraform ${message}"

    }
        catch (Exception error ) {
            print 'Failure using terraform init.'
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