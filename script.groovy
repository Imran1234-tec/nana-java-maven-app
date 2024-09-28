def buildJar() {
    echo "Building the application..."
    try {
        sh 'mvn package'
        echo "Build successful!"
    } catch (Exception e) {
        echo "Build failed: ${e.getMessage()}"
        currentBuild.result = 'FAILURE'
        error("Stopping the build due to the failure.")
    }
}


def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t nanajanashia/demo-app:jma-2.0 .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push nanajanashia/demo-app:jma-2.0'
    }
} 

def deployApp() {
    echo 'deploying the application...'
} 

return this
