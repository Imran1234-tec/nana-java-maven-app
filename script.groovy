def buildJar() {
    echo "Building the application..."
    try {
        def output = sh(script: 'mvn package', returnStdout: true).trim()
        echo output
        echo "Build successful!"
    } catch (Exception e) {
        echo "Build failed: ${e.getMessage()}"
        currentBuild.result = 'FAILURE'
        error("Stopping the build due to the failure.")
    }
}



def buildImage() {
    echo "Building the Docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        // Verify that the Dockerfile is present
        sh 'ls -al'

        // Build the Docker image
        sh 'docker build -t nanajanashia/demo-app:jma-2.0 .'

        // Log in to Docker Hub
        sh "echo ${PASS} | docker login -u ${USER} --password-stdin"

        // Push the Docker image
        sh 'docker push imrannanajanashia/demo-app:jma-2.0'
    }
}

def deployApp() {
    echo 'deploying the application...'
} 

return this
