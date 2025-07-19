# discovery of deployment solutions

Test project is a multi-module build created though an archetype:
https://maven.apache.org/archetypes/maven-archetype-j2ee-simple/

    mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-j2ee-simple -DarchetypeVersion=1.5

And few additions to [`pom.xml`](pom.xml) for some of the tests.

## basic test: deploy to local directory `target/deploy`

Instead of classical deployment to an HTTP(S) server (PUT), or scp or any other file-oriented server, we can easily test `maven-deploy-plugin:deploy` to deploy to a local directory using `altDeploymentRepository` parameter with a `file:` target url (instead of updating `pom.xml`'s `project.distributionManagement.repository.id`and `url`):

    mvn clean deploy -DaltDeploymentRepository=local::file:target/deploy

resulting files and directory tree is available in `target/deploy`, showing many individual files:

    tree target/deploy

Intentionally, we did not add sources, GPG signature nor javadoc, but a `central-prerequisites` profile is provided to add them to the structure.

    mvn clean deploy -DaltDeploymentRepository=local::file:target/deploy -Pcentral-prerequisites

## install/deploy "at end"

With install/deploy [at end](https://maven.apache.org/plugins/maven-deploy-plugin/deploy-mojo.html#deployAtEnd), instead of deploying the files for each module when the module is built, install/deploy is deferred on each module, to the last one that does every previous modules in a single run:

    mvn clean deploy -DaltDeploymentRepository=local::file:target/deploy -DinstallAtEnd -DdeployAtEnd

## manual upload to Maven Central Portal

Based on previous deploy to `target/deploy` local directory, it's easy to create a deployment bundle:

    tar cvf deploy-bundle.tar -C target/deploy org

then upload with Maven Central Portal ["Publish Component" UI](https://central.sonatype.org/publish/publish-portal-upload/) (publication is expected to fail because you don't have permissions on the groupId, but you can test and see the behaviour).

Upload can also be done [using `curl`](https://central.sonatype.org/publish/publish-portal-api/#uploading-a-deployment-bundle) to the [`/api/v1/publisher/upload`](https://central.sonatype.com/api-doc) API:

    curl --request POST \
    --verbose \
    --header 'Authorization: Bearer ZXhhbXBsZV91c2VybmFtZTpleGFtcGxlX3Bhc3N3b3Jk' \
    --form bundle=@deploy-bundle.tar \
    https://central.sonatype.com/api/v1/publisher/upload

## deploy with Sonatype Maven plugin

https://central.sonatype.org/publish/publish-portal-maven/

    mvn clean deploy -Pcentral-publishing

with a fake `publish-to-central` server id defined in `~/.m2/settings.xml` for putting fake credentials:

    <server>
      <id>publish-to-central</id>
      <username>fake</username>
	  <password>fake</password>
    </server>

Running with this plugin completely removes the `maven-deploy-plugin` from the build execution (as if it was not inherited from parent POM...), replacing with `central-publishing:publish`.

Individual files are staged to `target/central-staging`, before creating a publication bundle as `target/central-publishing/central-bundle.zip` and uploading it:

    tree target/central-*

TODO: see also [SNAPSHOT case](https://central.sonatype.org/publish/publish-portal-snapshots/)

## deploy with njord extension to Maven Resolver

https://maveniverse.eu/docs/njord/

    mvn clean deploy -DaltDeploymentRepository=id::njord:

With njord, `maven-deploy-plugin` stages the content to `njord:` url, which in fact stores content in `~/.njord/deploy-<counter>`:

    tree ~/.njord/deploy-*

And at Maven build end of execution (through [a MavenLifecycleParticipant](https://github.com/maveniverse/njord/blob/main/extension3/src/main/java/eu/maveniverse/maven/njord/extension3/NjordSessionLifecycleParticipant.java)), after `BUILD SUCCESS`, the staged content is eventually uploaded to Maven Central Portal upload API, or can be manipulated with [`njord-maven-plugin` provided goals](https://maveniverse.eu/docs/njord/plugin-documentation/plugin-info.html):

    mvn -Pnjord njord:list

TODO: dig into the upload aspects explained in [Using it](https://maveniverse.eu/docs/njord/using-it/) documentation...

TODO: test also everything with Maven 4
