#!groovy

multibranchPipelineJob('simple-app') {
    branchSources {
        git {
            remote('git@github.com:automatictester/simple-app.git')
            credentialsId('github-creds')
        }
    }
}
