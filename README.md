# deployment solutions study

Study to better understand [maven-deploy-plugin](https://maven.apache.org/plugins/maven-deploy-plugin/), how it deploys artifacts traditionnally to a [Maven repository](https://maven.apache.org/repository/layout.html),
and how the new Maven Central Portal publication changes the approach.

This study starts with a [base base project](1_DISCOVERY.md) for the problem space, with 6 tests:
1. [deploy to file:target/deploy](1.1_DISCOVERY-file.md)
2. [deploy at end](1.2_DISCOVERY-atEnd.md)
3. [manual upload of archive/bundle](1.3_DISCOVERY-upload-manual.md)
4. [use of Sonatype Central Publishing Maven Plugin](1.4_DISCOVERY-central-publishing.md)
5. [use of njord Maven Resolver extension](1.5_DISCOVERY-njord.md)
6. [use of JReleaser](1.6_DISCOVERY-jreleaser.md)


This will be followed by an evaluation based on differences:
- use of classical `pom.xml` configuration of publication target for SNASPHOT and release: `<project><distributionManagement><release><id>/<url>`,
- use of classical `settings.xml`'s `<server>` for credentials,
- configuration for basic md5+sha1 fingerprints and more advanced sha256+sha512,
- preparation for Maven 4,
- anything new or unexpected

Whatever solution is chosen, the fact that Maven Central Portal now provides an API where we upload one single archive deployment bundle help resrtying upload if anything goes unexpected.

## Option 1: deploy to file:/target/staging and manual/scripted upload

Pro: Configuring is easy, then script creating a tar or zip archive and upload with UI or `curl`.

Limitation: scripting for upload (like it or not)

## Option 2: Sonatype central-publishing-maven-plugin

Pro: Easy to configure.

Drawbacks:
- no configuration in `pom.xml` `distributionManagement`
- plugin not OSS
- plugin deletes `maven-deploy-plugin` at runtime, which confuses plugins like `maven-artifact-plugin`, `cyclonedx-maven-plugin` or `spdx-maven-plugin` which detect skipped modules for deployment
- does not work for Maven 4 (runtime deletion of a plugin binding will probably be a blocker)

## Option 3: njord

Pro: respects Maven's `pom.xml` `distributionManagement`, even hiding difference between SNAPSHOT and release, and hiding the fact that upload URL is different from download URL for Maven Central (something many tools don't manage well)

Con: not clear how to automate publication when there are multiple stores

Having a plugin to decouple Maven's build from publication to remote gives great opportunities to better manage the staging of content during build vs the upload/publication once build is ok.

## Option 4: JReleaser

Pro: does a lot

Con: does a lot, and replaces classical Maven release plugin
