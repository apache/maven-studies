
mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-j2ee-simple -DarchetypeVersion=1.5

mvn clean deploy -DaltDeploymentRepository=local::file:target/deploy

# intentionally SNAPSHOT, no GPG signature and no javadoc

mvn clean deploy -DaltDeploymentRepository=local::file:target/deploy -DinstallAtEnd -DdeployAtEnd

tar cvf deploy-bundle.tar -C target/deploy org
deploy with UI

mvn clean deploy -Pcentral-publishing

mvn clean deploy -Pnjord -DaltDeploymentRepository=id::njord:
