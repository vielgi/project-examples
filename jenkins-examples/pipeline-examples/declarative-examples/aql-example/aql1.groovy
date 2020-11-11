SERVER_ID = 'artifactory'
pipeline {
    agent any
    

    stages {
        stage ('Clone') {
            steps {
                git branch: 'master', url: "https://github.com/vielgi/project-examples"
            }
        }

        stage ('Upload') {
            steps {
                rtUpload (
                    buildName: 'holyFrog',
                    buildNumber: '42',
                    serverId: SERVER_ID, // Obtain an Artifactory server instance, defined in Jenkins --> Manage:
                    specPath: 'jenkins-examples/pipeline-examples/resources/recursive-flat-upload.json'
                )
            }
        }

        stage ('Download') {
            steps {
                rtDownload (
                    buildName: 'holyFrog',
                    buildNumber: '42',
                    serverId: SERVER_ID,
                    specPath: 'jenkins-examples/pipeline-examples/resources/aql-download.json'
                )
            }
        }

        stage ('Publish build info') {
            steps {
                rtPublishBuildInfo (
                    buildName: 'holyFrog',
                    buildNumber: '42',
                    serverId: SERVER_ID
                )
            }
        }
    }
}
