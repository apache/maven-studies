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

 TBD...
