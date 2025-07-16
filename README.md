
# init content

https://maven.apache.org/archetypes/maven-archetype-j2ee-simple/

    mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-j2ee-simple -DarchetypeVersion=1.5

# deploy to local directory `target/deploy`

    mvn clean deploy -DaltDeploymentRepository=local::file:target/deploy
    # intentionally no GPG signature and no javadoc

# deploy atEnd

    mvn clean deploy -DaltDeploymentRepository=local::file:target/deploy -DinstallAtEnd -DdeployAtEnd

# manual upload to Maven Central Portal

    tar cvf deploy-bundle.tar -C target/deploy org

then deploy with UI

# deploy with Sonatype Maven plugin

https://central.sonatype.org/publish/publish-portal-maven/

    mvn clean deploy -Pcentral-publishing

with a fake server defined in `~/.m2/settings.xml`

    <server>
      <id>publish-to-central</id>
      <username>fake</username>
	  <password>fake</password>
    </server>

# deploy with njord extension to Maven Resolver

https://maveniverse.eu/docs/njord/

    mvn clean deploy -Pnjord -DaltDeploymentRepository=id::njord:
