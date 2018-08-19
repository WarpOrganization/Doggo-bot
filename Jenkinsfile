node('master') {

    stage('prepare') {
        properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '20')),
                    disableConcurrentBuilds()])
        checkout scm
        sh './gradlew clean'
    }

    stage('build') {
        sh './gradlew jar'
    }

    stage('archive') {
        archiveArtifacts 'build/libs/*.jar'
    }

}