# AWS Lambda Unit Test Runner (experimental)

## Sample request

```json
{
    "targetDir": "/tmp/lambda-test-runner/",
    "command": "./mvnw test -Dmaven.repo.local=/tmp/.m2",
    "logFile": "/tmp/output.log",
    "repoUri": "https://github.com/automatictester/lambda-test-runner.git"
}
```

## Required environment variables

To be set in AWS Console:

```bash
MAVEN_USER_HOME=/tmp/.m2
```

## Current status

- Cloning Git repo and running external process works fine.
- Maven execution is failing due to JDK not available on the underlying image (only JRE is available).
- I don't like any of the workarounds currently available.
- No further development planned for now.
