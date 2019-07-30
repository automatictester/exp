### Install archetype

```bash
mvn clean install
```

### Generate project from artifact

```bash
mvn archetype:generate \
   -B \
   -DarchetypeGroupId=uk.co.automatictester \
   -DarchetypeArtifactId=jmeter-lightning-maven-plugin-archetype \
   -DarchetypeVersion=1.0.0-SNAPSHOT \
   -DgroupId=my.group \
   -DartifactId=my-artifact \
   -Dversion=1.0.0-SNAPSHOT
```

### Run tests in newly generated project

```bash
mvn clean verify
```
