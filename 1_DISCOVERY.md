# discovery of deployment solutions

Test project is a multi-module build (7 modules/`pom.xml`) created though an archetype:
https://maven.apache.org/archetypes/maven-archetype-j2ee-simple/

    mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-j2ee-simple -DarchetypeVersion=1.5

And few [additions](https://github.com/apache/maven-studies/compare/e67f7f87981ef0e8cfbe8c75da11693e40a93a41...deploy#diff-9c5fb3d1b7e3b0f54bc5c4182965c4fe1f9023d449017cece3005d3f90e8e4d8) to [`pom.xml`](pom.xml) for some of the tests.

<details><summary>mvn -V validate</summary>

```
$ mvn -V validate
Apache Maven 3.9.11 (3e54c93a704957b63ee3494413a2b544fd3d825b)
Maven home: /home/herve/.sdkman/candidates/maven/current
Java version: 1.8.0_345, vendor: Azul Systems, Inc., runtime: /home/herve/.sdkman/candidates/java/8.0.345-zulu/jre
Default locale: fr_FR, platform encoding: UTF-8
OS name: "linux", version: "6.8.0-63-generic", arch: "amd64", family: "unix"
[INFO] Scanning for projects...
[INFO] Njord 0.8.2 session created
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Build Order:
[INFO] 
[INFO] deploy study root                                                  [pom]
[INFO] sub projects                                                       [pom]
[INFO] logging                                                            [jar]
[INFO] core project classes                                               [jar]
[INFO] enterprise java beans                                              [ejb]
[INFO] servlet                                                            [war]
[INFO] ear assembly                                                       [ear]
[INFO] 
[INFO] ------------------< org.apache.maven.studies:deploy >-------------------
[INFO] Building deploy study root 1.0                                     [1/7]
[INFO]   from pom.xml
[INFO] --------------------------------[ pom ]---------------------------------
[INFO] 
[INFO] -----------------< org.apache.maven.studies:projects >------------------
[INFO] Building sub projects 1.0                                          [2/7]
[INFO]   from projects/pom.xml
[INFO] --------------------------------[ pom ]---------------------------------
[INFO] 
[INFO] ------------------< org.apache.maven.studies:logging >------------------
[INFO] Building logging 1.0                                               [3/7]
[INFO]   from projects/logging/pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --------------< org.apache.maven.studies:primary-source >---------------
[INFO] Building core project classes 1.0                                  [4/7]
[INFO]   from projects/primary-source/pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] -------------------< org.apache.maven.studies:ejbs >--------------------
[INFO] Building enterprise java beans 1.0                                 [5/7]
[INFO]   from projects/ejbs/pom.xml
[INFO] --------------------------------[ ejb ]---------------------------------
[INFO] 
[INFO] ------------------< org.apache.maven.studies:servlet >------------------
[INFO] Building servlet 1.0                                               [6/7]
[INFO]   from projects/servlet/pom.xml
[INFO] --------------------------------[ war ]---------------------------------
[INFO] 
[INFO] --------------------< org.apache.maven.studies:ear >--------------------
[INFO] Building ear assembly 1.0                                          [7/7]
[INFO]   from projects/ear/pom.xml
[INFO] --------------------------------[ ear ]---------------------------------
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for deploy study root 1.0:
[INFO] 
[INFO] deploy study root .................................. SUCCESS [  0.001 s]
[INFO] sub projects ....................................... SUCCESS [  0.001 s]
[INFO] logging ............................................ SUCCESS [  0.000 s]
[INFO] core project classes ............................... SUCCESS [  0.000 s]
[INFO] enterprise java beans .............................. SUCCESS [  0.000 s]
[INFO] servlet ............................................ SUCCESS [  0.000 s]
[INFO] ear assembly ....................................... SUCCESS [  0.001 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.175 s
[INFO] Finished at: 2025-07-19T11:15:14+02:00
[INFO] ------------------------------------------------------------------------
[INFO] Njord session closed
```
</details>

<details><summary>mvn -V buildplan:list</summary>

```
$ mvn -V buildplan:list
Apache Maven 3.9.11 (3e54c93a704957b63ee3494413a2b544fd3d825b)
Maven home: /home/herve/.sdkman/candidates/maven/current
Java version: 1.8.0_345, vendor: Azul Systems, Inc., runtime: /home/herve/.sdkman/candidates/java/8.0.345-zulu/jre
Default locale: fr_FR, platform encoding: UTF-8
OS name: "linux", version: "6.8.0-63-generic", arch: "amd64", family: "unix"
[INFO] Scanning for projects...
[INFO] Njord 0.8.2 session created
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Build Order:
[INFO] 
[INFO] deploy study root                                                  [pom]
[INFO] sub projects                                                       [pom]
[INFO] logging                                                            [jar]
[INFO] core project classes                                               [jar]
[INFO] enterprise java beans                                              [ejb]
[INFO] servlet                                                            [war]
[INFO] ear assembly                                                       [ear]
[INFO] 
[INFO] ------------------< org.apache.maven.studies:deploy >-------------------
[INFO] Building deploy study root 1.0                                     [1/7]
[INFO]   from pom.xml
[INFO] --------------------------------[ pom ]---------------------------------
[INFO] 
[INFO] --- buildplan:2.2.2:list (default-cli) @ deploy ---
[INFO] Build Plan for deploy study root: 
--------------------------------------------------------------------
PHASE   | PLUGIN               | VERSION | GOAL    | EXECUTION ID   
--------------------------------------------------------------------
install | maven-install-plugin | 3.1.4   | install | default-install
deploy  | maven-deploy-plugin  | 3.1.4   | deploy  | default-deploy 
[INFO] 
[INFO] -----------------< org.apache.maven.studies:projects >------------------
[INFO] Building sub projects 1.0                                          [2/7]
[INFO]   from projects/pom.xml
[INFO] --------------------------------[ pom ]---------------------------------
[INFO] 
[INFO] --- buildplan:2.2.2:list (default-cli) @ projects ---
[INFO] Build Plan for sub projects: 
--------------------------------------------------------------------
PHASE   | PLUGIN               | VERSION | GOAL    | EXECUTION ID   
--------------------------------------------------------------------
install | maven-install-plugin | 3.1.4   | install | default-install
deploy  | maven-deploy-plugin  | 3.1.4   | deploy  | default-deploy 
[INFO] 
[INFO] ------------------< org.apache.maven.studies:logging >------------------
[INFO] Building logging 1.0                                               [3/7]
[INFO]   from projects/logging/pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- buildplan:2.2.2:list (default-cli) @ logging ---
[INFO] Build Plan for logging: 
-------------------------------------------------------------------------------------------------
PHASE                  | PLUGIN                 | VERSION | GOAL          | EXECUTION ID         
-------------------------------------------------------------------------------------------------
process-resources      | maven-resources-plugin | 3.3.1   | resources     | default-resources    
compile                | maven-compiler-plugin  | 3.14.0  | compile       | default-compile      
process-test-resources | maven-resources-plugin | 3.3.1   | testResources | default-testResources
test-compile           | maven-compiler-plugin  | 3.14.0  | testCompile   | default-testCompile  
test                   | maven-surefire-plugin  | 3.5.3   | test          | default-test         
package                | maven-jar-plugin       | 3.4.2   | jar           | default-jar          
install                | maven-install-plugin   | 3.1.4   | install       | default-install      
deploy                 | maven-deploy-plugin    | 3.1.4   | deploy        | default-deploy       
[INFO] 
[INFO] --------------< org.apache.maven.studies:primary-source >---------------
[INFO] Building core project classes 1.0                                  [4/7]
[INFO]   from projects/primary-source/pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- buildplan:2.2.2:list (default-cli) @ primary-source ---
[INFO] Build Plan for core project classes: 
-------------------------------------------------------------------------------------------------
PHASE                  | PLUGIN                 | VERSION | GOAL          | EXECUTION ID         
-------------------------------------------------------------------------------------------------
process-resources      | maven-resources-plugin | 3.3.1   | resources     | default-resources    
compile                | maven-compiler-plugin  | 3.14.0  | compile       | default-compile      
process-test-resources | maven-resources-plugin | 3.3.1   | testResources | default-testResources
test-compile           | maven-compiler-plugin  | 3.14.0  | testCompile   | default-testCompile  
test                   | maven-surefire-plugin  | 3.5.3   | test          | default-test         
package                | maven-jar-plugin       | 3.4.2   | jar           | default-jar          
install                | maven-install-plugin   | 3.1.4   | install       | default-install      
deploy                 | maven-deploy-plugin    | 3.1.4   | deploy        | default-deploy       
[INFO] 
[INFO] -------------------< org.apache.maven.studies:ejbs >--------------------
[INFO] Building enterprise java beans 1.0                                 [5/7]
[INFO]   from projects/ejbs/pom.xml
[INFO] --------------------------------[ ejb ]---------------------------------
[INFO] 
[INFO] --- buildplan:2.2.2:list (default-cli) @ ejbs ---
[INFO] Build Plan for enterprise java beans: 
-------------------------------------------------------------------------------------------------
PHASE                  | PLUGIN                 | VERSION | GOAL          | EXECUTION ID         
-------------------------------------------------------------------------------------------------
process-resources      | maven-resources-plugin | 3.3.1   | resources     | default-resources    
compile                | maven-compiler-plugin  | 3.14.0  | compile       | default-compile      
process-test-resources | maven-resources-plugin | 3.3.1   | testResources | default-testResources
test-compile           | maven-compiler-plugin  | 3.14.0  | testCompile   | default-testCompile  
test                   | maven-surefire-plugin  | 3.5.3   | test          | default-test         
package                | maven-ejb-plugin       | 3.2.1   | ejb           | default-ejb          
install                | maven-install-plugin   | 3.1.4   | install       | default-install      
deploy                 | maven-deploy-plugin    | 3.1.4   | deploy        | default-deploy       
[INFO] 
[INFO] ------------------< org.apache.maven.studies:servlet >------------------
[INFO] Building servlet 1.0                                               [6/7]
[INFO]   from projects/servlet/pom.xml
[INFO] --------------------------------[ war ]---------------------------------
[INFO] 
[INFO] --- buildplan:2.2.2:list (default-cli) @ servlet ---
[INFO] Build Plan for servlet: 
-------------------------------------------------------------------------------------------------
PHASE                  | PLUGIN                 | VERSION | GOAL          | EXECUTION ID         
-------------------------------------------------------------------------------------------------
process-resources      | maven-resources-plugin | 3.3.1   | resources     | default-resources    
compile                | maven-compiler-plugin  | 3.14.0  | compile       | default-compile      
process-test-resources | maven-resources-plugin | 3.3.1   | testResources | default-testResources
test-compile           | maven-compiler-plugin  | 3.14.0  | testCompile   | default-testCompile  
test                   | maven-surefire-plugin  | 3.5.3   | test          | default-test         
package                | maven-war-plugin       | 3.4.0   | war           | default-war          
install                | maven-install-plugin   | 3.1.4   | install       | default-install      
deploy                 | maven-deploy-plugin    | 3.1.4   | deploy        | default-deploy       
[INFO] 
[INFO] --------------------< org.apache.maven.studies:ear >--------------------
[INFO] Building ear assembly 1.0                                          [7/7]
[INFO]   from projects/ear/pom.xml
[INFO] --------------------------------[ ear ]---------------------------------
[INFO] 
[INFO] --- buildplan:2.2.2:list (default-cli) @ ear ---
[INFO] Build Plan for ear assembly: 
-------------------------------------------------------------------------------------------------------------------
PHASE              | PLUGIN                 | VERSION | GOAL                     | EXECUTION ID                    
-------------------------------------------------------------------------------------------------------------------
generate-resources | maven-ear-plugin       | 3.4.0   | generate-application-xml | default-generate-application-xml
process-resources  | maven-resources-plugin | 3.3.1   | resources                | default-resources               
package            | maven-ear-plugin       | 3.4.0   | ear                      | default-ear                     
install            | maven-install-plugin   | 3.1.4   | install                  | default-install                 
deploy             | maven-deploy-plugin    | 3.1.4   | deploy                   | default-deploy                  
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for deploy study root 1.0:
[INFO] 
[INFO] deploy study root .................................. SUCCESS [  0.175 s]
[INFO] sub projects ....................................... SUCCESS [  0.003 s]
[INFO] logging ............................................ SUCCESS [  0.003 s]
[INFO] core project classes ............................... SUCCESS [  0.003 s]
[INFO] enterprise java beans .............................. SUCCESS [  0.003 s]
[INFO] servlet ............................................ SUCCESS [  0.003 s]
[INFO] ear assembly ....................................... SUCCESS [  0.003 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.469 s
[INFO] Finished at: 2025-07-19T11:16:28+02:00
[INFO] ------------------------------------------------------------------------
[INFO] Njord session closed
```
</details>

<details><summary>(Maven 4) mvn -V buildplan:list</summary>

```
$ mvn -V buildplan:list
Apache Maven 4.0.0-rc-4 (bed0f8174bf728978f86fac533aa38a9511f3872)
Maven home: /home/herve/.sdkman/candidates/maven/4.0.0-rc-4
Java version: 17.0.8, vendor: Azul Systems, Inc., runtime: /home/herve/.sdkman/candidates/java/17.0.8-zulu
Default locale: fr_FR, platform encoding: UTF-8
OS name: "linux", version: "6.8.0-63-generic", arch: "amd64", family: "unix"
[WARNING] Unable to find the root directory. Create a .mvn directory in the root directory or add the root="true" attribute on the root project's model to identify it.
[INFO] Scanning for projects...
[INFO] Njord 0.8.2 session created
[INFO] --------------------------------------------------------------------------------------------------------------------------
[INFO] Reactor Build Order:
[INFO] 
[INFO] deploy study root                                                                                                    [pom]
[INFO] sub projects                                                                                                         [pom]
[INFO] logging                                                                                                              [jar]
[INFO] core project classes                                                                                                 [jar]
[INFO] enterprise java beans                                                                                                [ejb]
[INFO] servlet                                                                                                              [war]
[INFO] ear assembly                                                                                                         [ear]
[INFO] 
...
```
</details>

## basic test: deploy to local directory `target/deploy`

Instead of classical deployment to an HTTP(S) server (PUT), or scp or any other file-oriented server, we can easily test `maven-deploy-plugin:deploy` to deploy to a local directory using `altDeploymentRepository` parameter with a `file:` target url (instead of updating `pom.xml`'s `project.distributionManagement.repository.id`and `url`):

    mvn clean deploy -DaltDeploymentRepository=local::file:target/deploy

<details><summary>mvn clean deploy -DaltDeploymentRepository=local::file:target/deploy</summary>

```
$ mvn clean deploy -DaltDeploymentRepository=local::file:target/deploy
[INFO] Scanning for projects...
[INFO] Njord 0.8.2 session created
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Build Order:
[INFO] 
[INFO] deploy study root                                                  [pom]
[INFO] sub projects                                                       [pom]
[INFO] logging                                                            [jar]
[INFO] core project classes                                               [jar]
[INFO] enterprise java beans                                              [ejb]
[INFO] servlet                                                            [war]
[INFO] ear assembly                                                       [ear]
[INFO] 
[INFO] ------------------< org.apache.maven.studies:deploy >-------------------
[INFO] Building deploy study root 1.0                                     [1/7]
[INFO]   from pom.xml
[INFO] --------------------------------[ pom ]---------------------------------
[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ deploy ---
[INFO] Deleting /home/herve/dev/maven/sources/studies/deploy/target
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ deploy ---
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/pom.xml to /home/herve/.m2/repository/org/apache/maven/studies/deploy/1.0/deploy-1.0.pom
[INFO] 
[INFO] --- deploy:3.1.4:deploy (default-deploy) @ deploy ---
[INFO] Using alternate deployment repository local::file:target/deploy
Uploading to local: file:target/deploy/org/apache/maven/studies/deploy/1.0/deploy-1.0.pom
Uploaded to local: file:target/deploy/org/apache/maven/studies/deploy/1.0/deploy-1.0.pom (6.3 kB at 1.3 MB/s)
Downloading from local: file:target/deploy/org/apache/maven/studies/deploy/maven-metadata.xml
Uploading to local: file:target/deploy/org/apache/maven/studies/deploy/maven-metadata.xml
Uploaded to local: file:target/deploy/org/apache/maven/studies/deploy/maven-metadata.xml (306 B at 306 kB/s)
[INFO] 
[INFO] -----------------< org.apache.maven.studies:projects >------------------
[INFO] Building sub projects 1.0                                          [2/7]
[INFO]   from projects/pom.xml
[INFO] --------------------------------[ pom ]---------------------------------
[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ projects ---
[INFO] Deleting /home/herve/dev/maven/sources/studies/deploy/projects/target
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ projects ---
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/pom.xml to /home/herve/.m2/repository/org/apache/maven/studies/projects/1.0/projects-1.0.pom
[INFO] 
[INFO] --- deploy:3.1.4:deploy (default-deploy) @ projects ---
[INFO] Using alternate deployment repository local::file:target/deploy
Uploading to local: file:target/deploy/org/apache/maven/studies/projects/1.0/projects-1.0.pom
Uploaded to local: file:target/deploy/org/apache/maven/studies/projects/1.0/projects-1.0.pom (696 B at 696 kB/s)
Downloading from local: file:target/deploy/org/apache/maven/studies/projects/maven-metadata.xml
Uploading to local: file:target/deploy/org/apache/maven/studies/projects/maven-metadata.xml
Uploaded to local: file:target/deploy/org/apache/maven/studies/projects/maven-metadata.xml (308 B)
[INFO] 
...
[INFO] --- deploy:3.1.4:deploy (default-deploy) @ ear ---
[INFO] Using alternate deployment repository local::file:target/deploy
Uploading to local: file:target/deploy/org/apache/maven/studies/ear/1.0/ear-1.0.pom
Uploaded to local: file:target/deploy/org/apache/maven/studies/ear/1.0/ear-1.0.pom (1.4 kB)
Uploading to local: file:target/deploy/org/apache/maven/studies/ear/1.0/ear-1.0.ear
Uploaded to local: file:target/deploy/org/apache/maven/studies/ear/1.0/ear-1.0.ear (6.1 kB at 6.1 MB/s)
Downloading from local: file:target/deploy/org/apache/maven/studies/ear/maven-metadata.xml
Uploading to local: file:target/deploy/org/apache/maven/studies/ear/maven-metadata.xml
Uploaded to local: file:target/deploy/org/apache/maven/studies/ear/maven-metadata.xml (303 B)
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for deploy study root 1.0:
[INFO] 
[INFO] deploy study root .................................. SUCCESS [  0.119 s]
[INFO] sub projects ....................................... SUCCESS [  0.006 s]
[INFO] logging ............................................ SUCCESS [  0.317 s]
[INFO] core project classes ............................... SUCCESS [  0.018 s]
[INFO] enterprise java beans .............................. SUCCESS [  0.176 s]
[INFO] servlet ............................................ SUCCESS [  0.172 s]
[INFO] ear assembly ....................................... SUCCESS [  0.155 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.160 s
[INFO] Finished at: 2025-07-19T11:29:00+02:00
[INFO] ------------------------------------------------------------------------
[INFO] Njord session closed
```
</details>

\
resulting files and directory tree is available in `target/deploy`, showing 57 (=3*19) individual files (= 7 `.pom`+`maven-metadata.xml` = one per goupId/artifactId/version, 5 `.jar`/`.ear`/`.war` => 19 files, each with 2 fingerprints `.md5`/`.sha1`):

    tree target/deploy

<details><summary>tree target/deploy</summary>

```
$ tree target/deploy
target/deploy
└── org
    └── apache
        └── maven
            └── studies
                ├── deploy
                │   ├── 1.0
                │   │   ├── deploy-1.0.pom
                │   │   ├── deploy-1.0.pom.md5
                │   │   └── deploy-1.0.pom.sha1
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   └── maven-metadata.xml.sha1
                ├── ear
                │   ├── 1.0
                │   │   ├── ear-1.0.ear
                │   │   ├── ear-1.0.ear.md5
                │   │   ├── ear-1.0.ear.sha1
                │   │   ├── ear-1.0.pom
                │   │   ├── ear-1.0.pom.md5
                │   │   └── ear-1.0.pom.sha1
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   └── maven-metadata.xml.sha1
                ├── ejbs
                │   ├── 1.0
                │   │   ├── ejbs-1.0.jar
                │   │   ├── ejbs-1.0.jar.md5
                │   │   ├── ejbs-1.0.jar.sha1
                │   │   ├── ejbs-1.0.pom
                │   │   ├── ejbs-1.0.pom.md5
                │   │   └── ejbs-1.0.pom.sha1
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   └── maven-metadata.xml.sha1
                ├── logging
                │   ├── 1.0
                │   │   ├── logging-1.0.jar
                │   │   ├── logging-1.0.jar.md5
                │   │   ├── logging-1.0.jar.sha1
                │   │   ├── logging-1.0.pom
                │   │   ├── logging-1.0.pom.md5
                │   │   └── logging-1.0.pom.sha1
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   └── maven-metadata.xml.sha1
                ├── primary-source
                │   ├── 1.0
                │   │   ├── primary-source-1.0.jar
                │   │   ├── primary-source-1.0.jar.md5
                │   │   ├── primary-source-1.0.jar.sha1
                │   │   ├── primary-source-1.0.pom
                │   │   ├── primary-source-1.0.pom.md5
                │   │   └── primary-source-1.0.pom.sha1
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   └── maven-metadata.xml.sha1
                ├── projects
                │   ├── 1.0
                │   │   ├── projects-1.0.pom
                │   │   ├── projects-1.0.pom.md5
                │   │   └── projects-1.0.pom.sha1
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   └── maven-metadata.xml.sha1
                └── servlet
                    ├── 1.0
                    │   ├── servlet-1.0.pom
                    │   ├── servlet-1.0.pom.md5
                    │   ├── servlet-1.0.pom.sha1
                    │   ├── servlet-1.0.war
                    │   ├── servlet-1.0.war.md5
                    │   └── servlet-1.0.war.sha1
                    ├── maven-metadata.xml
                    ├── maven-metadata.xml.md5
                    └── maven-metadata.xml.sha1

19 directories, 57 files
```
</details>

\
Intentionally, we did not add sources, GPG signature nor javadoc, but a `central-prerequisites` profile is provided to add them to the structure.

    mvn clean deploy -DaltDeploymentRepository=local::file:target/deploy -Pcentral-prerequisites

that will add `maven-source-plugin`, `maven-javadoc-plugin` and `maven-gpg-plugin`:
<details><summary>mvn buildplan:list -Pcentral-prerequisites</summary>

```
...
[INFO] ------------------< org.apache.maven.studies:servlet >------------------
[INFO] Building servlet 1.0                                               [6/7]
[INFO]   from projects/servlet/pom.xml
[INFO] --------------------------------[ war ]---------------------------------
[INFO] 
[INFO] --- buildplan:2.2.2:list (default-cli) @ servlet ---
[INFO] Build Plan for servlet: 
-------------------------------------------------------------------------------------------------
PHASE                  | PLUGIN                 | VERSION | GOAL          | EXECUTION ID         
-------------------------------------------------------------------------------------------------
process-resources      | maven-resources-plugin | 3.3.1   | resources     | default-resources    
compile                | maven-compiler-plugin  | 3.14.0  | compile       | default-compile      
process-test-resources | maven-resources-plugin | 3.3.1   | testResources | default-testResources
test-compile           | maven-compiler-plugin  | 3.14.0  | testCompile   | default-testCompile  
test                   | maven-surefire-plugin  | 3.5.3   | test          | default-test         
package                | maven-war-plugin       | 3.4.0   | war           | default-war          
package                | maven-source-plugin    | 3.3.1   | jar-no-fork   | attach-sources       
package                | maven-javadoc-plugin   | 3.11.2  | jar           | attach-javadocs      
verify                 | maven-gpg-plugin       | 3.2.8   | sign          | sign-artifacts       
install                | maven-install-plugin   | 3.1.4   | install       | default-install      
deploy                 | maven-deploy-plugin    | 3.1.4   | deploy        | default-deploy       
...
```
</details>

\
After execution with `central-prerequisites` profile, each artifact file has an additional `.asc` PGP detached signature (notice: not `maven-metadata.xml`), and some packagings provide `-sources.jar` and `-javadoc.jar`, which gives us now 73 files (= 3 * 7 for metadata + 4 * 13):
<details><summary>tree target/deploy</summary>

```
$ tree target/deploy
target/deploy
└── org
    └── apache
        └── maven
            └── studies
                ├── deploy
                │   ├── 1.0
                │   │   ├── deploy-1.0.pom
                │   │   ├── deploy-1.0.pom.asc
                │   │   ├── deploy-1.0.pom.md5
                │   │   └── deploy-1.0.pom.sha1
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   └── maven-metadata.xml.sha1
                ├── ear
                │   ├── 1.0
                │   │   ├── ear-1.0.ear
                │   │   ├── ear-1.0.ear.asc
                │   │   ├── ear-1.0.ear.md5
                │   │   ├── ear-1.0.ear.sha1
                │   │   ├── ear-1.0.pom
                │   │   ├── ear-1.0.pom.asc
                │   │   ├── ear-1.0.pom.md5
                │   │   └── ear-1.0.pom.sha1
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   └── maven-metadata.xml.sha1
                ├── ejbs
                │   ├── 1.0
                │   │   ├── ejbs-1.0.jar
                │   │   ├── ejbs-1.0.jar.asc
                │   │   ├── ejbs-1.0.jar.md5
                │   │   ├── ejbs-1.0.jar.sha1
                │   │   ├── ejbs-1.0.pom
                │   │   ├── ejbs-1.0.pom.asc
                │   │   ├── ejbs-1.0.pom.md5
                │   │   ├── ejbs-1.0.pom.sha1
                │   │   ├── ejbs-1.0-sources.jar
                │   │   ├── ejbs-1.0-sources.jar.asc
                │   │   ├── ejbs-1.0-sources.jar.md5
                │   │   └── ejbs-1.0-sources.jar.sha1
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   └── maven-metadata.xml.sha1
                ├── logging
                │   ├── 1.0
                │   │   ├── logging-1.0.jar
                │   │   ├── logging-1.0.jar.asc
                │   │   ├── logging-1.0.jar.md5
                │   │   ├── logging-1.0.jar.sha1
                │   │   ├── logging-1.0.pom
                │   │   ├── logging-1.0.pom.asc
                │   │   ├── logging-1.0.pom.md5
                │   │   └── logging-1.0.pom.sha1
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   └── maven-metadata.xml.sha1
                ├── primary-source
                │   ├── 1.0
                │   │   ├── primary-source-1.0.jar
                │   │   ├── primary-source-1.0.jar.asc
                │   │   ├── primary-source-1.0.jar.md5
                │   │   ├── primary-source-1.0.jar.sha1
                │   │   ├── primary-source-1.0.pom
                │   │   ├── primary-source-1.0.pom.asc
                │   │   ├── primary-source-1.0.pom.md5
                │   │   └── primary-source-1.0.pom.sha1
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   └── maven-metadata.xml.sha1
                ├── projects
                │   ├── 1.0
                │   │   ├── projects-1.0.pom
                │   │   ├── projects-1.0.pom.asc
                │   │   ├── projects-1.0.pom.md5
                │   │   └── projects-1.0.pom.sha1
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   └── maven-metadata.xml.sha1
                └── servlet
                    ├── 1.0
                    │   ├── servlet-1.0.pom
                    │   ├── servlet-1.0.pom.asc
                    │   ├── servlet-1.0.pom.md5
                    │   ├── servlet-1.0.pom.sha1
                    │   ├── servlet-1.0.war
                    │   ├── servlet-1.0.war.asc
                    │   ├── servlet-1.0.war.md5
                    │   └── servlet-1.0.war.sha1
                    ├── maven-metadata.xml
                    ├── maven-metadata.xml.md5
                    └── maven-metadata.xml.sha1

19 directories, 73 files
```
</details>

\
When run with Maven 4, the output in addition contains build POMs = `*-build.pom`, which brings us to 101 files:
<details><summary>tree target/deploy</summary>

```
$ tree target/deploy
target/deploy
└── org
    └── apache
        └── maven
            └── studies
                ├── deploy
                │   ├── 1.0
                │   │   ├── deploy-1.0-build.pom
                │   │   ├── deploy-1.0-build.pom.asc
                │   │   ├── deploy-1.0-build.pom.md5
                │   │   ├── deploy-1.0-build.pom.sha1
                │   │   ├── deploy-1.0.pom
                │   │   ├── deploy-1.0.pom.asc
                │   │   ├── deploy-1.0.pom.md5
                │   │   └── deploy-1.0.pom.sha1
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   └── maven-metadata.xml.sha1
                ├── ear
                │   ├── 1.0
                │   │   ├── ear-1.0-build.pom
                │   │   ├── ear-1.0-build.pom.asc
                │   │   ├── ear-1.0-build.pom.md5
                │   │   ├── ear-1.0-build.pom.sha1
                │   │   ├── ear-1.0.ear
                │   │   ├── ear-1.0.ear.asc
                │   │   ├── ear-1.0.ear.md5
                │   │   ├── ear-1.0.ear.sha1
                │   │   ├── ear-1.0.pom
                │   │   ├── ear-1.0.pom.asc
                │   │   ├── ear-1.0.pom.md5
                │   │   └── ear-1.0.pom.sha1
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   └── maven-metadata.xml.sha1
                ├── ejbs
                │   ├── 1.0
                │   │   ├── ejbs-1.0-build.pom
                │   │   ├── ejbs-1.0-build.pom.asc
                │   │   ├── ejbs-1.0-build.pom.md5
                │   │   ├── ejbs-1.0-build.pom.sha1
                │   │   ├── ejbs-1.0.jar
                │   │   ├── ejbs-1.0.jar.asc
                │   │   ├── ejbs-1.0.jar.md5
                │   │   ├── ejbs-1.0.jar.sha1
                │   │   ├── ejbs-1.0.pom
                │   │   ├── ejbs-1.0.pom.asc
                │   │   ├── ejbs-1.0.pom.md5
                │   │   ├── ejbs-1.0.pom.sha1
                │   │   ├── ejbs-1.0-sources.jar
                │   │   ├── ejbs-1.0-sources.jar.asc
                │   │   ├── ejbs-1.0-sources.jar.md5
                │   │   └── ejbs-1.0-sources.jar.sha1
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   └── maven-metadata.xml.sha1
                ├── logging
                │   ├── 1.0
                │   │   ├── logging-1.0-build.pom
                │   │   ├── logging-1.0-build.pom.asc
                │   │   ├── logging-1.0-build.pom.md5
                │   │   ├── logging-1.0-build.pom.sha1
                │   │   ├── logging-1.0.jar
                │   │   ├── logging-1.0.jar.asc
                │   │   ├── logging-1.0.jar.md5
                │   │   ├── logging-1.0.jar.sha1
                │   │   ├── logging-1.0.pom
                │   │   ├── logging-1.0.pom.asc
                │   │   ├── logging-1.0.pom.md5
                │   │   └── logging-1.0.pom.sha1
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   └── maven-metadata.xml.sha1
                ├── primary-source
                │   ├── 1.0
                │   │   ├── primary-source-1.0-build.pom
                │   │   ├── primary-source-1.0-build.pom.asc
                │   │   ├── primary-source-1.0-build.pom.md5
                │   │   ├── primary-source-1.0-build.pom.sha1
                │   │   ├── primary-source-1.0.jar
                │   │   ├── primary-source-1.0.jar.asc
                │   │   ├── primary-source-1.0.jar.md5
                │   │   ├── primary-source-1.0.jar.sha1
                │   │   ├── primary-source-1.0.pom
                │   │   ├── primary-source-1.0.pom.asc
                │   │   ├── primary-source-1.0.pom.md5
                │   │   └── primary-source-1.0.pom.sha1
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   └── maven-metadata.xml.sha1
                ├── projects
                │   ├── 1.0
                │   │   ├── projects-1.0-build.pom
                │   │   ├── projects-1.0-build.pom.asc
                │   │   ├── projects-1.0-build.pom.md5
                │   │   ├── projects-1.0-build.pom.sha1
                │   │   ├── projects-1.0.pom
                │   │   ├── projects-1.0.pom.asc
                │   │   ├── projects-1.0.pom.md5
                │   │   └── projects-1.0.pom.sha1
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   └── maven-metadata.xml.sha1
                └── servlet
                    ├── 1.0
                    │   ├── servlet-1.0-build.pom
                    │   ├── servlet-1.0-build.pom.asc
                    │   ├── servlet-1.0-build.pom.md5
                    │   ├── servlet-1.0-build.pom.sha1
                    │   ├── servlet-1.0.pom
                    │   ├── servlet-1.0.pom.asc
                    │   ├── servlet-1.0.pom.md5
                    │   ├── servlet-1.0.pom.sha1
                    │   ├── servlet-1.0.war
                    │   ├── servlet-1.0.war.asc
                    │   ├── servlet-1.0.war.md5
                    │   └── servlet-1.0.war.sha1
                    ├── maven-metadata.xml
                    ├── maven-metadata.xml.md5
                    └── maven-metadata.xml.sha1

19 directories, 101 files
```
</details>

TODO: add discussion for `.sha256` and `.sha512` addition, which would go from 101 files to 155 (= 7 metadata * 5 (4 fingerprints) + 20 artifact files * 6 (.asc + 4 fingerprints))... (notice: when some tools publish fingeprints for .asc files, they add 20 * 4 = 80 additional files...)

## install/deploy "at end"

With install/deploy [at end](https://maven.apache.org/plugins/maven-deploy-plugin/deploy-mojo.html#deployAtEnd), instead of deploying the files for each module when the module is built, install/deploy is deferred on each module, to the last one that does every previous modules in a single run:

    mvn clean deploy -DaltDeploymentRepository=local::file:target/deploy -DinstallAtEnd -DdeployAtEnd

For each module, install/deploy does just an `[INFO] Deferring <install/deploy> for <groupId>:<artifactId>:<version> at end`, waiting for the last module to do all the work in one run:
<details><summary>mvn clean deploy -DaltDeploymentRepository=local::file:target/deploy -DinstallAtEnd -DdeployAtEnd</summary>

```
$ mvn clean deploy -DaltDeploymentRepository=local::file:target/deploy -DinstallAtEnd -DdeployAtEnd
[INFO] Scanning for projects...
[INFO] Njord 0.8.2 session created
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Build Order:
[INFO] 
[INFO] deploy study root                                                  [pom]
[INFO] sub projects                                                       [pom]
[INFO] logging                                                            [jar]
[INFO] core project classes                                               [jar]
[INFO] enterprise java beans                                              [ejb]
[INFO] servlet                                                            [war]
[INFO] ear assembly                                                       [ear]
[INFO] 
[INFO] ------------------< org.apache.maven.studies:deploy >-------------------
[INFO] Building deploy study root 1.0                                     [1/7]
[INFO]   from pom.xml
[INFO] --------------------------------[ pom ]---------------------------------
[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ deploy ---
[INFO] Deleting /home/herve/dev/maven/sources/studies/deploy/target
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ deploy ---
[INFO] Deferring install for org.apache.maven.studies:deploy:1.0 at end
[INFO] 
[INFO] --- deploy:3.1.4:deploy (default-deploy) @ deploy ---
[INFO] Deferring deploy for org.apache.maven.studies:deploy:1.0 at end
[INFO] 
[INFO] -----------------< org.apache.maven.studies:projects >------------------
[INFO] Building sub projects 1.0                                          [2/7]
[INFO]   from projects/pom.xml
[INFO] --------------------------------[ pom ]---------------------------------
[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ projects ---
[INFO] Deleting /home/herve/dev/maven/sources/studies/deploy/projects/target
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ projects ---
[INFO] Deferring install for org.apache.maven.studies:projects:1.0 at end
[INFO] 
[INFO] --- deploy:3.1.4:deploy (default-deploy) @ projects ---
[INFO] Deferring deploy for org.apache.maven.studies:projects:1.0 at end
[INFO] 
[INFO] ------------------< org.apache.maven.studies:logging >------------------
[INFO] Building logging 1.0                                               [3/7]
[INFO]   from projects/logging/pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ logging ---
[INFO] Deleting /home/herve/dev/maven/sources/studies/deploy/projects/logging/target
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ logging ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/logging/src/main/resources
[INFO] 
[INFO] --- compiler:3.14.0:compile (default-compile) @ logging ---
[INFO] No sources to compile
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ logging ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/logging/src/test/resources
[INFO] 
[INFO] --- compiler:3.14.0:testCompile (default-testCompile) @ logging ---
[INFO] No sources to compile
[INFO] 
[INFO] --- surefire:3.5.3:test (default-test) @ logging ---
[INFO] No tests to run.
[INFO] 
[INFO] --- jar:3.4.2:jar (default-jar) @ logging ---
[WARNING] JAR will be empty - no content was marked for inclusion!
[INFO] Building jar: /home/herve/dev/maven/sources/studies/deploy/projects/logging/target/logging-1.0.jar
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ logging ---
[INFO] Deferring install for org.apache.maven.studies:logging:1.0 at end
[INFO] 
[INFO] --- deploy:3.1.4:deploy (default-deploy) @ logging ---
[INFO] Deferring deploy for org.apache.maven.studies:logging:1.0 at end
[INFO] 
[INFO] --------------< org.apache.maven.studies:primary-source >---------------
[INFO] Building core project classes 1.0                                  [4/7]
[INFO]   from projects/primary-source/pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ primary-source ---
[INFO] Deleting /home/herve/dev/maven/sources/studies/deploy/projects/primary-source/target
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ primary-source ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/primary-source/src/main/resources
[INFO] 
[INFO] --- compiler:3.14.0:compile (default-compile) @ primary-source ---
[INFO] No sources to compile
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ primary-source ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/primary-source/src/test/resources
[INFO] 
[INFO] --- compiler:3.14.0:testCompile (default-testCompile) @ primary-source ---
[INFO] No sources to compile
[INFO] 
[INFO] --- surefire:3.5.3:test (default-test) @ primary-source ---
[INFO] No tests to run.
[INFO] 
[INFO] --- jar:3.4.2:jar (default-jar) @ primary-source ---
[WARNING] JAR will be empty - no content was marked for inclusion!
[INFO] Building jar: /home/herve/dev/maven/sources/studies/deploy/projects/primary-source/target/primary-source-1.0.jar
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ primary-source ---
[INFO] Deferring install for org.apache.maven.studies:primary-source:1.0 at end
[INFO] 
[INFO] --- deploy:3.1.4:deploy (default-deploy) @ primary-source ---
[INFO] Deferring deploy for org.apache.maven.studies:primary-source:1.0 at end
[INFO] 
[INFO] -------------------< org.apache.maven.studies:ejbs >--------------------
[INFO] Building enterprise java beans 1.0                                 [5/7]
[INFO]   from projects/ejbs/pom.xml
[INFO] --------------------------------[ ejb ]---------------------------------
[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ ejbs ---
[INFO] Deleting /home/herve/dev/maven/sources/studies/deploy/projects/ejbs/target
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ ejbs ---
[INFO] Copying 1 resource from src/main/resources to target/classes
[INFO] 
[INFO] --- compiler:3.14.0:compile (default-compile) @ ejbs ---
[INFO] No sources to compile
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ ejbs ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/ejbs/src/test/resources
[INFO] 
[INFO] --- compiler:3.14.0:testCompile (default-testCompile) @ ejbs ---
[INFO] No sources to compile
[INFO] 
[INFO] --- surefire:3.5.3:test (default-test) @ ejbs ---
[INFO] No tests to run.
[INFO] 
[INFO] --- ejb:3.2.1:ejb (default-ejb) @ ejbs ---
[INFO] Building EJB ejbs-1.0 with EJB version 3.1
[INFO] Building jar: /home/herve/dev/maven/sources/studies/deploy/projects/ejbs/target/ejbs-1.0.jar
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ ejbs ---
[INFO] Deferring install for org.apache.maven.studies:ejbs:1.0 at end
[INFO] 
[INFO] --- deploy:3.1.4:deploy (default-deploy) @ ejbs ---
[INFO] Deferring deploy for org.apache.maven.studies:ejbs:1.0 at end
[INFO] 
[INFO] ------------------< org.apache.maven.studies:servlet >------------------
[INFO] Building servlet 1.0                                               [6/7]
[INFO]   from projects/servlet/pom.xml
[INFO] --------------------------------[ war ]---------------------------------
[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ servlet ---
[INFO] Deleting /home/herve/dev/maven/sources/studies/deploy/projects/servlet/target
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ servlet ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/servlet/src/main/resources
[INFO] 
[INFO] --- compiler:3.14.0:compile (default-compile) @ servlet ---
[INFO] No sources to compile
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ servlet ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/servlet/src/test/resources
[INFO] 
[INFO] --- compiler:3.14.0:testCompile (default-testCompile) @ servlet ---
[INFO] No sources to compile
[INFO] 
[INFO] --- surefire:3.5.3:test (default-test) @ servlet ---
[INFO] No tests to run.
[INFO] 
[INFO] --- war:3.4.0:war (default-war) @ servlet ---
[INFO] Packaging webapp
[INFO] Assembling webapp [servlet] in [/home/herve/dev/maven/sources/studies/deploy/projects/servlet/target/servlet-1.0]
[INFO] Processing war project
[INFO] Copying webapp resources [/home/herve/dev/maven/sources/studies/deploy/projects/servlet/src/main/webapp]
[INFO] Building war: /home/herve/dev/maven/sources/studies/deploy/projects/servlet/target/servlet-1.0.war
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ servlet ---
[INFO] Deferring install for org.apache.maven.studies:servlet:1.0 at end
[INFO] 
[INFO] --- deploy:3.1.4:deploy (default-deploy) @ servlet ---
[INFO] Deferring deploy for org.apache.maven.studies:servlet:1.0 at end
[INFO] 
[INFO] --------------------< org.apache.maven.studies:ear >--------------------
[INFO] Building ear assembly 1.0                                          [7/7]
[INFO]   from projects/ear/pom.xml
[INFO] --------------------------------[ ear ]---------------------------------
[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ ear ---
[INFO] Deleting /home/herve/dev/maven/sources/studies/deploy/projects/ear/target
[INFO] 
[INFO] --- ear:3.4.0:generate-application-xml (default-generate-application-xml) @ ear ---
[INFO] Generating application.xml
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ ear ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/ear/src/main/resources
[INFO] 
[INFO] --- ear:3.4.0:ear (default-ear) @ ear ---
[INFO] Copying artifact [ejb:org.apache.maven.studies:ejbs:1.0] to [org.apache.maven.studies-ejbs-1.0.jar]
[INFO] Copying artifact [war:org.apache.maven.studies:servlet:1.0] to [org.apache.maven.studies-servlet-1.0.war]
[INFO] Copying artifact [jar:org.apache.maven.studies:primary-source:1.0] to [org.apache.maven.studies-primary-source-1.0.jar]
[INFO] Copying artifact [jar:org.apache.maven.studies:logging:1.0] to [org.apache.maven.studies-logging-1.0.jar]
[INFO] Building ear: /home/herve/dev/maven/sources/studies/deploy/projects/ear/target/ear-1.0.ear
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ ear ---
[INFO] Deferring install for org.apache.maven.studies:ear:1.0 at end
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/pom.xml to /home/herve/.m2/repository/org/apache/maven/studies/deploy/1.0/deploy-1.0.pom
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/pom.xml to /home/herve/.m2/repository/org/apache/maven/studies/projects/1.0/projects-1.0.pom
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/logging/pom.xml to /home/herve/.m2/repository/org/apache/maven/studies/logging/1.0/logging-1.0.pom
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/logging/target/logging-1.0.jar to /home/herve/.m2/repository/org/apache/maven/studies/logging/1.0/logging-1.0.jar
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/primary-source/pom.xml to /home/herve/.m2/repository/org/apache/maven/studies/primary-source/1.0/primary-source-1.0.pom
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/primary-source/target/primary-source-1.0.jar to /home/herve/.m2/repository/org/apache/maven/studies/primary-source/1.0/primary-source-1.0.jar
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/ejbs/pom.xml to /home/herve/.m2/repository/org/apache/maven/studies/ejbs/1.0/ejbs-1.0.pom
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/ejbs/target/ejbs-1.0.jar to /home/herve/.m2/repository/org/apache/maven/studies/ejbs/1.0/ejbs-1.0.jar
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/servlet/pom.xml to /home/herve/.m2/repository/org/apache/maven/studies/servlet/1.0/servlet-1.0.pom
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/servlet/target/servlet-1.0.war to /home/herve/.m2/repository/org/apache/maven/studies/servlet/1.0/servlet-1.0.war
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/ear/pom.xml to /home/herve/.m2/repository/org/apache/maven/studies/ear/1.0/ear-1.0.pom
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/ear/target/ear-1.0.ear to /home/herve/.m2/repository/org/apache/maven/studies/ear/1.0/ear-1.0.ear
[INFO] 
[INFO] --- deploy:3.1.4:deploy (default-deploy) @ ear ---
[INFO] Using alternate deployment repository local::file:target/deploy
[INFO] Using alternate deployment repository local::file:target/deploy
[INFO] Using alternate deployment repository local::file:target/deploy
[INFO] Using alternate deployment repository local::file:target/deploy
[INFO] Using alternate deployment repository local::file:target/deploy
[INFO] Using alternate deployment repository local::file:target/deploy
[INFO] Using alternate deployment repository local::file:target/deploy
Uploading to local: file:target/deploy/org/apache/maven/studies/deploy/1.0/deploy-1.0.pom
Uploaded to local: file:target/deploy/org/apache/maven/studies/deploy/1.0/deploy-1.0.pom (6.3 kB at 1.6 MB/s)
Uploading to local: file:target/deploy/org/apache/maven/studies/projects/1.0/projects-1.0.pom
Uploading to local: file:target/deploy/org/apache/maven/studies/logging/1.0/logging-1.0.pom
Uploading to local: file:target/deploy/org/apache/maven/studies/logging/1.0/logging-1.0.jar
Uploading to local: file:target/deploy/org/apache/maven/studies/primary-source/1.0/primary-source-1.0.pom
Uploading to local: file:target/deploy/org/apache/maven/studies/primary-source/1.0/primary-source-1.0.jar
Uploaded to local: file:target/deploy/org/apache/maven/studies/primary-source/1.0/primary-source-1.0.pom (1.0 kB at 507 kB/s)
Uploading to local: file:target/deploy/org/apache/maven/studies/ejbs/1.0/ejbs-1.0.pom
Uploaded to local: file:target/deploy/org/apache/maven/studies/projects/1.0/projects-1.0.pom (696 B at 348 kB/s)
Uploading to local: file:target/deploy/org/apache/maven/studies/ejbs/1.0/ejbs-1.0.jar
Uploaded to local: file:target/deploy/org/apache/maven/studies/logging/1.0/logging-1.0.pom (492 B at 164 kB/s)
Uploading to local: file:target/deploy/org/apache/maven/studies/servlet/1.0/servlet-1.0.pom
Uploaded to local: file:target/deploy/org/apache/maven/studies/logging/1.0/logging-1.0.jar (1.5 kB at 363 kB/s)
Uploading to local: file:target/deploy/org/apache/maven/studies/servlet/1.0/servlet-1.0.war
Uploaded to local: file:target/deploy/org/apache/maven/studies/ejbs/1.0/ejbs-1.0.pom (1.1 kB at 285 kB/s)
Uploading to local: file:target/deploy/org/apache/maven/studies/ear/1.0/ear-1.0.pom
Uploaded to local: file:target/deploy/org/apache/maven/studies/primary-source/1.0/primary-source-1.0.jar (1.7 kB at 413 kB/s)
Uploading to local: file:target/deploy/org/apache/maven/studies/ear/1.0/ear-1.0.ear
Uploaded to local: file:target/deploy/org/apache/maven/studies/ear/1.0/ear-1.0.pom (1.4 kB at 284 kB/s)
Uploaded to local: file:target/deploy/org/apache/maven/studies/ejbs/1.0/ejbs-1.0.jar (1.8 kB at 293 kB/s)
Uploaded to local: file:target/deploy/org/apache/maven/studies/servlet/1.0/servlet-1.0.war (1.5 kB at 254 kB/s)
Uploaded to local: file:target/deploy/org/apache/maven/studies/servlet/1.0/servlet-1.0.pom (718 B at 120 kB/s)
Uploaded to local: file:target/deploy/org/apache/maven/studies/ear/1.0/ear-1.0.ear (6.2 kB at 1.0 MB/s)
Downloading from local: file:target/deploy/org/apache/maven/studies/deploy/maven-metadata.xml
Downloading from local: file:target/deploy/org/apache/maven/studies/projects/maven-metadata.xml
Downloading from local: file:target/deploy/org/apache/maven/studies/logging/maven-metadata.xml
Downloading from local: file:target/deploy/org/apache/maven/studies/primary-source/maven-metadata.xml
Downloading from local: file:target/deploy/org/apache/maven/studies/ejbs/maven-metadata.xml
Downloading from local: file:target/deploy/org/apache/maven/studies/servlet/maven-metadata.xml
Downloading from local: file:target/deploy/org/apache/maven/studies/ear/maven-metadata.xml
Uploading to local: file:target/deploy/org/apache/maven/studies/deploy/maven-metadata.xml
Uploaded to local: file:target/deploy/org/apache/maven/studies/deploy/maven-metadata.xml (306 B)
Uploading to local: file:target/deploy/org/apache/maven/studies/projects/maven-metadata.xml
Uploading to local: file:target/deploy/org/apache/maven/studies/logging/maven-metadata.xml
Uploading to local: file:target/deploy/org/apache/maven/studies/primary-source/maven-metadata.xml
Uploading to local: file:target/deploy/org/apache/maven/studies/ejbs/maven-metadata.xml
Uploading to local: file:target/deploy/org/apache/maven/studies/servlet/maven-metadata.xml
Uploaded to local: file:target/deploy/org/apache/maven/studies/projects/maven-metadata.xml (308 B at 154 kB/s)
Uploading to local: file:target/deploy/org/apache/maven/studies/ear/maven-metadata.xml
Uploaded to local: file:target/deploy/org/apache/maven/studies/primary-source/maven-metadata.xml (314 B at 157 kB/s)
Uploaded to local: file:target/deploy/org/apache/maven/studies/logging/maven-metadata.xml (307 B at 154 kB/s)
Uploaded to local: file:target/deploy/org/apache/maven/studies/ear/maven-metadata.xml (303 B at 152 kB/s)
Uploaded to local: file:target/deploy/org/apache/maven/studies/servlet/maven-metadata.xml (307 B at 154 kB/s)
Uploaded to local: file:target/deploy/org/apache/maven/studies/ejbs/maven-metadata.xml (304 B at 152 kB/s)
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for deploy study root 1.0:
[INFO] 
[INFO] deploy study root .................................. SUCCESS [  0.078 s]
[INFO] sub projects ....................................... SUCCESS [  0.002 s]
[INFO] logging ............................................ SUCCESS [  0.278 s]
[INFO] core project classes ............................... SUCCESS [  0.014 s]
[INFO] enterprise java beans .............................. SUCCESS [  0.158 s]
[INFO] servlet ............................................ SUCCESS [  0.161 s]
[INFO] ear assembly ....................................... SUCCESS [  0.157 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.025 s
[INFO] Finished at: 2025-07-19T14:23:57+02:00
[INFO] ------------------------------------------------------------------------
[INFO] Njord session closed
```
</details>

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

<details><summary>mvn clean deploy -Pcentral-publishing</summary>

```
$ mvn clean deploy -Pcentral-publishing
[INFO] Scanning for projects...
[INFO] Inspecting build with total of 7 modules
[INFO] Installing Central Publishing features
[INFO] Njord 0.8.2 session created
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Build Order:
[INFO] 
[INFO] deploy study root                                                  [pom]
[INFO] sub projects                                                       [pom]
[INFO] logging                                                            [jar]
[INFO] core project classes                                               [jar]
[INFO] enterprise java beans                                              [ejb]
[INFO] servlet                                                            [war]
[INFO] ear assembly                                                       [ear]
[INFO] 
[INFO] ------------------< org.apache.maven.studies:deploy >-------------------
[INFO] Building deploy study root 1.0                                     [1/7]
[INFO]   from pom.xml
[INFO] --------------------------------[ pom ]---------------------------------
[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ deploy ---
[INFO] Deleting /home/herve/dev/maven/sources/studies/deploy/target
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ deploy ---
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/pom.xml to /home/herve/.m2/repository/org/apache/maven/studies/deploy/1.0/deploy-1.0.pom
[INFO] 
[INFO] --- central-publishing:0.8.0:publish (injected-central-publishing) @ deploy ---
[INFO] Using Central baseUrl: https://central.sonatype.com
[INFO] Using credentials from server id publish-to-central in settings.xml
[INFO] Using Usertoken auth, with namecode: GSGwGb_
[INFO] Staging 1 files
[INFO] Staging /home/herve/dev/maven/sources/studies/deploy/pom.xml
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/pom.xml to /home/herve/dev/maven/sources/studies/deploy/target/central-staging/org/apache/maven/studies/deploy/1.0/deploy-1.0.pom
[INFO] Pre Bundling - deleted /home/herve/dev/maven/sources/studies/deploy/target/central-staging/org/apache/maven/studies/deploy/maven-metadata-central-staging.xml
[INFO] Generate checksums for dir: org/apache/maven/studies/deploy/1.0
[INFO] 
[INFO] -----------------< org.apache.maven.studies:projects >------------------
[INFO] Building sub projects 1.0                                          [2/7]
[INFO]   from projects/pom.xml
[INFO] --------------------------------[ pom ]---------------------------------
[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ projects ---
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ projects ---
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/pom.xml to /home/herve/.m2/repository/org/apache/maven/studies/projects/1.0/projects-1.0.pom
[INFO] 
[INFO] --- central-publishing:0.8.0:publish (injected-central-publishing) @ projects ---
[INFO] Using Central baseUrl: https://central.sonatype.com
[INFO] Using credentials from server id publish-to-central in settings.xml
[INFO] Using Usertoken auth, with namecode: GSGwGb_
[INFO] Staging 1 files
[INFO] Staging /home/herve/dev/maven/sources/studies/deploy/projects/pom.xml
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/pom.xml to /home/herve/dev/maven/sources/studies/deploy/target/central-staging/org/apache/maven/studies/projects/1.0/projects-1.0.pom
[INFO] Pre Bundling - deleted /home/herve/dev/maven/sources/studies/deploy/target/central-staging/org/apache/maven/studies/projects/maven-metadata-central-staging.xml
[INFO] Generate checksums for dir: org/apache/maven/studies/projects/1.0
[INFO] 
[INFO] ------------------< org.apache.maven.studies:logging >------------------
[INFO] Building logging 1.0                                               [3/7]
[INFO]   from projects/logging/pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ logging ---
[INFO] Deleting /home/herve/dev/maven/sources/studies/deploy/projects/logging/target
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ logging ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/logging/src/main/resources
[INFO] 
[INFO] --- compiler:3.14.0:compile (default-compile) @ logging ---
[INFO] No sources to compile
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ logging ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/logging/src/test/resources
[INFO] 
[INFO] --- compiler:3.14.0:testCompile (default-testCompile) @ logging ---
[INFO] No sources to compile
[INFO] 
[INFO] --- surefire:3.5.3:test (default-test) @ logging ---
[INFO] No tests to run.
[INFO] 
[INFO] --- jar:3.4.2:jar (default-jar) @ logging ---
[WARNING] JAR will be empty - no content was marked for inclusion!
[INFO] Building jar: /home/herve/dev/maven/sources/studies/deploy/projects/logging/target/logging-1.0.jar
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ logging ---
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/logging/pom.xml to /home/herve/.m2/repository/org/apache/maven/studies/logging/1.0/logging-1.0.pom
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/logging/target/logging-1.0.jar to /home/herve/.m2/repository/org/apache/maven/studies/logging/1.0/logging-1.0.jar
[INFO] 
[INFO] --- central-publishing:0.8.0:publish (injected-central-publishing) @ logging ---
[INFO] Using Central baseUrl: https://central.sonatype.com
[INFO] Using credentials from server id publish-to-central in settings.xml
[INFO] Using Usertoken auth, with namecode: GSGwGb_
[INFO] Staging 1 files
[INFO] Staging /home/herve/dev/maven/sources/studies/deploy/projects/logging/target/logging-1.0.jar
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/logging/target/logging-1.0.jar to /home/herve/dev/maven/sources/studies/deploy/target/central-staging/org/apache/maven/studies/logging/1.0/logging-1.0.jar
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/logging/pom.xml to /home/herve/dev/maven/sources/studies/deploy/target/central-staging/org/apache/maven/studies/logging/1.0/logging-1.0.pom
[INFO] Pre Bundling - deleted /home/herve/dev/maven/sources/studies/deploy/target/central-staging/org/apache/maven/studies/logging/maven-metadata-central-staging.xml
[INFO] Generate checksums for dir: org/apache/maven/studies/logging/1.0
[INFO] 
[INFO] --------------< org.apache.maven.studies:primary-source >---------------
[INFO] Building core project classes 1.0                                  [4/7]
[INFO]   from projects/primary-source/pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ primary-source ---
[INFO] Deleting /home/herve/dev/maven/sources/studies/deploy/projects/primary-source/target
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ primary-source ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/primary-source/src/main/resources
[INFO] 
[INFO] --- compiler:3.14.0:compile (default-compile) @ primary-source ---
[INFO] No sources to compile
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ primary-source ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/primary-source/src/test/resources
[INFO] 
[INFO] --- compiler:3.14.0:testCompile (default-testCompile) @ primary-source ---
[INFO] No sources to compile
[INFO] 
[INFO] --- surefire:3.5.3:test (default-test) @ primary-source ---
[INFO] No tests to run.
[INFO] 
[INFO] --- jar:3.4.2:jar (default-jar) @ primary-source ---
[WARNING] JAR will be empty - no content was marked for inclusion!
[INFO] Building jar: /home/herve/dev/maven/sources/studies/deploy/projects/primary-source/target/primary-source-1.0.jar
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ primary-source ---
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/primary-source/pom.xml to /home/herve/.m2/repository/org/apache/maven/studies/primary-source/1.0/primary-source-1.0.pom
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/primary-source/target/primary-source-1.0.jar to /home/herve/.m2/repository/org/apache/maven/studies/primary-source/1.0/primary-source-1.0.jar
[INFO] 
[INFO] --- central-publishing:0.8.0:publish (injected-central-publishing) @ primary-source ---
[INFO] Using Central baseUrl: https://central.sonatype.com
[INFO] Using credentials from server id publish-to-central in settings.xml
[INFO] Using Usertoken auth, with namecode: GSGwGb_
[INFO] Staging 1 files
[INFO] Staging /home/herve/dev/maven/sources/studies/deploy/projects/primary-source/target/primary-source-1.0.jar
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/primary-source/target/primary-source-1.0.jar to /home/herve/dev/maven/sources/studies/deploy/target/central-staging/org/apache/maven/studies/primary-source/1.0/primary-source-1.0.jar
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/primary-source/pom.xml to /home/herve/dev/maven/sources/studies/deploy/target/central-staging/org/apache/maven/studies/primary-source/1.0/primary-source-1.0.pom
[INFO] Pre Bundling - deleted /home/herve/dev/maven/sources/studies/deploy/target/central-staging/org/apache/maven/studies/primary-source/maven-metadata-central-staging.xml
[INFO] Generate checksums for dir: org/apache/maven/studies/primary-source/1.0
[INFO] 
[INFO] -------------------< org.apache.maven.studies:ejbs >--------------------
[INFO] Building enterprise java beans 1.0                                 [5/7]
[INFO]   from projects/ejbs/pom.xml
[INFO] --------------------------------[ ejb ]---------------------------------
[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ ejbs ---
[INFO] Deleting /home/herve/dev/maven/sources/studies/deploy/projects/ejbs/target
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ ejbs ---
[INFO] Copying 1 resource from src/main/resources to target/classes
[INFO] 
[INFO] --- compiler:3.14.0:compile (default-compile) @ ejbs ---
[INFO] No sources to compile
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ ejbs ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/ejbs/src/test/resources
[INFO] 
[INFO] --- compiler:3.14.0:testCompile (default-testCompile) @ ejbs ---
[INFO] No sources to compile
[INFO] 
[INFO] --- surefire:3.5.3:test (default-test) @ ejbs ---
[INFO] No tests to run.
[INFO] 
[INFO] --- ejb:3.2.1:ejb (default-ejb) @ ejbs ---
[INFO] Building EJB ejbs-1.0 with EJB version 3.1
[INFO] Building jar: /home/herve/dev/maven/sources/studies/deploy/projects/ejbs/target/ejbs-1.0.jar
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ ejbs ---
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/ejbs/pom.xml to /home/herve/.m2/repository/org/apache/maven/studies/ejbs/1.0/ejbs-1.0.pom
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/ejbs/target/ejbs-1.0.jar to /home/herve/.m2/repository/org/apache/maven/studies/ejbs/1.0/ejbs-1.0.jar
[INFO] 
[INFO] --- central-publishing:0.8.0:publish (injected-central-publishing) @ ejbs ---
[INFO] Using Central baseUrl: https://central.sonatype.com
[INFO] Using credentials from server id publish-to-central in settings.xml
[INFO] Using Usertoken auth, with namecode: GSGwGb_
[INFO] Staging 1 files
[INFO] Staging /home/herve/dev/maven/sources/studies/deploy/projects/ejbs/target/ejbs-1.0.jar
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/ejbs/target/ejbs-1.0.jar to /home/herve/dev/maven/sources/studies/deploy/target/central-staging/org/apache/maven/studies/ejbs/1.0/ejbs-1.0.jar
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/ejbs/pom.xml to /home/herve/dev/maven/sources/studies/deploy/target/central-staging/org/apache/maven/studies/ejbs/1.0/ejbs-1.0.pom
[INFO] Pre Bundling - deleted /home/herve/dev/maven/sources/studies/deploy/target/central-staging/org/apache/maven/studies/ejbs/maven-metadata-central-staging.xml
[INFO] Generate checksums for dir: org/apache/maven/studies/ejbs/1.0
[INFO] 
[INFO] ------------------< org.apache.maven.studies:servlet >------------------
[INFO] Building servlet 1.0                                               [6/7]
[INFO]   from projects/servlet/pom.xml
[INFO] --------------------------------[ war ]---------------------------------
[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ servlet ---
[INFO] Deleting /home/herve/dev/maven/sources/studies/deploy/projects/servlet/target
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ servlet ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/servlet/src/main/resources
[INFO] 
[INFO] --- compiler:3.14.0:compile (default-compile) @ servlet ---
[INFO] No sources to compile
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ servlet ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/servlet/src/test/resources
[INFO] 
[INFO] --- compiler:3.14.0:testCompile (default-testCompile) @ servlet ---
[INFO] No sources to compile
[INFO] 
[INFO] --- surefire:3.5.3:test (default-test) @ servlet ---
[INFO] No tests to run.
[INFO] 
[INFO] --- war:3.4.0:war (default-war) @ servlet ---
[INFO] Packaging webapp
[INFO] Assembling webapp [servlet] in [/home/herve/dev/maven/sources/studies/deploy/projects/servlet/target/servlet-1.0]
[INFO] Processing war project
[INFO] Copying webapp resources [/home/herve/dev/maven/sources/studies/deploy/projects/servlet/src/main/webapp]
[INFO] Building war: /home/herve/dev/maven/sources/studies/deploy/projects/servlet/target/servlet-1.0.war
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ servlet ---
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/servlet/pom.xml to /home/herve/.m2/repository/org/apache/maven/studies/servlet/1.0/servlet-1.0.pom
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/servlet/target/servlet-1.0.war to /home/herve/.m2/repository/org/apache/maven/studies/servlet/1.0/servlet-1.0.war
[INFO] 
[INFO] --- central-publishing:0.8.0:publish (injected-central-publishing) @ servlet ---
[INFO] Using Central baseUrl: https://central.sonatype.com
[INFO] Using credentials from server id publish-to-central in settings.xml
[INFO] Using Usertoken auth, with namecode: GSGwGb_
[INFO] Staging 1 files
[INFO] Staging /home/herve/dev/maven/sources/studies/deploy/projects/servlet/target/servlet-1.0.war
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/servlet/target/servlet-1.0.war to /home/herve/dev/maven/sources/studies/deploy/target/central-staging/org/apache/maven/studies/servlet/1.0/servlet-1.0.war
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/servlet/pom.xml to /home/herve/dev/maven/sources/studies/deploy/target/central-staging/org/apache/maven/studies/servlet/1.0/servlet-1.0.pom
[INFO] Pre Bundling - deleted /home/herve/dev/maven/sources/studies/deploy/target/central-staging/org/apache/maven/studies/servlet/maven-metadata-central-staging.xml
[INFO] Generate checksums for dir: org/apache/maven/studies/servlet/1.0
[INFO] 
[INFO] --------------------< org.apache.maven.studies:ear >--------------------
[INFO] Building ear assembly 1.0                                          [7/7]
[INFO]   from projects/ear/pom.xml
[INFO] --------------------------------[ ear ]---------------------------------
[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ ear ---
[INFO] Deleting /home/herve/dev/maven/sources/studies/deploy/projects/ear/target
[INFO] 
[INFO] --- ear:3.4.0:generate-application-xml (default-generate-application-xml) @ ear ---
[INFO] Generating application.xml
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ ear ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/ear/src/main/resources
[INFO] 
[INFO] --- ear:3.4.0:ear (default-ear) @ ear ---
[INFO] Copying artifact [ejb:org.apache.maven.studies:ejbs:1.0] to [org.apache.maven.studies-ejbs-1.0.jar]
[INFO] Copying artifact [war:org.apache.maven.studies:servlet:1.0] to [org.apache.maven.studies-servlet-1.0.war]
[INFO] Copying artifact [jar:org.apache.maven.studies:primary-source:1.0] to [org.apache.maven.studies-primary-source-1.0.jar]
[INFO] Copying artifact [jar:org.apache.maven.studies:logging:1.0] to [org.apache.maven.studies-logging-1.0.jar]
[INFO] Building ear: /home/herve/dev/maven/sources/studies/deploy/projects/ear/target/ear-1.0.ear
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ ear ---
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/ear/pom.xml to /home/herve/.m2/repository/org/apache/maven/studies/ear/1.0/ear-1.0.pom
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/ear/target/ear-1.0.ear to /home/herve/.m2/repository/org/apache/maven/studies/ear/1.0/ear-1.0.ear
[INFO] 
[INFO] --- central-publishing:0.8.0:publish (injected-central-publishing) @ ear ---
[INFO] Using Central baseUrl: https://central.sonatype.com
[INFO] Using credentials from server id publish-to-central in settings.xml
[INFO] Using Usertoken auth, with namecode: GSGwGb_
[INFO] Staging 1 files
[INFO] Staging /home/herve/dev/maven/sources/studies/deploy/projects/ear/target/ear-1.0.ear
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/ear/target/ear-1.0.ear to /home/herve/dev/maven/sources/studies/deploy/target/central-staging/org/apache/maven/studies/ear/1.0/ear-1.0.ear
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/ear/pom.xml to /home/herve/dev/maven/sources/studies/deploy/target/central-staging/org/apache/maven/studies/ear/1.0/ear-1.0.pom
[INFO] Pre Bundling - deleted /home/herve/dev/maven/sources/studies/deploy/target/central-staging/org/apache/maven/studies/ear/maven-metadata-central-staging.xml
[INFO] Generate checksums for dir: org/apache/maven/studies/ear/1.0
[INFO] Going to create /home/herve/dev/maven/sources/studies/deploy/target/central-publishing/central-bundle.zip by bundling content at /home/herve/dev/maven/sources/studies/deploy/target/central-staging
[INFO] Created bundle successfully /home/herve/dev/maven/sources/studies/deploy/target/central-staging/central-bundle.zip
[INFO] Going to upload /home/herve/dev/maven/sources/studies/deploy/target/central-publishing/central-bundle.zip
...
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for deploy study root 1.0:
[INFO] 
[INFO] deploy study root .................................. SUCCESS [  0.159 s]
[INFO] sub projects ....................................... SUCCESS [  0.007 s]
[INFO] logging ............................................ SUCCESS [  0.286 s]
[INFO] core project classes ............................... SUCCESS [  0.016 s]
[INFO] enterprise java beans .............................. SUCCESS [  0.127 s]
[INFO] servlet ............................................ SUCCESS [  0.125 s]
[INFO] ear assembly ....................................... FAILURE [  1.068 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.061 s
[INFO] Finished at: 2025-07-19T14:28:00+02:00
[INFO] ------------------------------------------------------------------------
[INFO] Njord session closed
[ERROR] Failed to execute goal org.sonatype.central:central-publishing-maven-plugin:0.8.0:publish (injected-central-publishing) on project ear: Execution injected-central-publishing of goal org.sonatype.central:central-publishing-maven-plugin:0.8.0:publish failed: Deployment Deployment failed while publishing -> [Help 1]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/PluginExecutionException
[ERROR] 
[ERROR] After correcting the problems, you can resume the build with the command
[ERROR]   mvn <args> -rf :ear
```
</details>

\
Individual files are staged to `target/central-staging`, before creating a publication bundle as `target/central-publishing/central-bundle.zip` and uploading it:

    tree target/central-*

Notice: there is no `maven-metadata.xml`, but `.sha256` and `.sha512`:

<details><summary>tree target/central-*</summary>

```
$ tree target/central-*
target/central-deferred
target/central-publishing
└── central-bundle.zip
target/central-staging
└── org
    └── apache
        └── maven
            └── studies
                ├── deploy
                │   └── 1.0
                │       ├── deploy-1.0.pom
                │       ├── deploy-1.0.pom.md5
                │       ├── deploy-1.0.pom.sha1
                │       ├── deploy-1.0.pom.sha256
                │       └── deploy-1.0.pom.sha512
                ├── ear
                │   └── 1.0
                │       ├── ear-1.0.ear
                │       ├── ear-1.0.ear.md5
                │       ├── ear-1.0.ear.sha1
                │       ├── ear-1.0.ear.sha256
                │       ├── ear-1.0.ear.sha512
                │       ├── ear-1.0.pom
                │       ├── ear-1.0.pom.md5
                │       ├── ear-1.0.pom.sha1
                │       ├── ear-1.0.pom.sha256
                │       └── ear-1.0.pom.sha512
                ├── ejbs
                │   └── 1.0
                │       ├── ejbs-1.0.jar
                │       ├── ejbs-1.0.jar.md5
                │       ├── ejbs-1.0.jar.sha1
                │       ├── ejbs-1.0.jar.sha256
                │       ├── ejbs-1.0.jar.sha512
                │       ├── ejbs-1.0.pom
                │       ├── ejbs-1.0.pom.md5
                │       ├── ejbs-1.0.pom.sha1
                │       ├── ejbs-1.0.pom.sha256
                │       └── ejbs-1.0.pom.sha512
                ├── logging
                │   └── 1.0
                │       ├── logging-1.0.jar
                │       ├── logging-1.0.jar.md5
                │       ├── logging-1.0.jar.sha1
                │       ├── logging-1.0.jar.sha256
                │       ├── logging-1.0.jar.sha512
                │       ├── logging-1.0.pom
                │       ├── logging-1.0.pom.md5
                │       ├── logging-1.0.pom.sha1
                │       ├── logging-1.0.pom.sha256
                │       └── logging-1.0.pom.sha512
                ├── primary-source
                │   └── 1.0
                │       ├── primary-source-1.0.jar
                │       ├── primary-source-1.0.jar.md5
                │       ├── primary-source-1.0.jar.sha1
                │       ├── primary-source-1.0.jar.sha256
                │       ├── primary-source-1.0.jar.sha512
                │       ├── primary-source-1.0.pom
                │       ├── primary-source-1.0.pom.md5
                │       ├── primary-source-1.0.pom.sha1
                │       ├── primary-source-1.0.pom.sha256
                │       └── primary-source-1.0.pom.sha512
                ├── projects
                │   └── 1.0
                │       ├── projects-1.0.pom
                │       ├── projects-1.0.pom.md5
                │       ├── projects-1.0.pom.sha1
                │       ├── projects-1.0.pom.sha256
                │       └── projects-1.0.pom.sha512
                └── servlet
                    └── 1.0
                        ├── servlet-1.0.pom
                        ├── servlet-1.0.pom.md5
                        ├── servlet-1.0.pom.sha1
                        ├── servlet-1.0.pom.sha256
                        ├── servlet-1.0.pom.sha512
                        ├── servlet-1.0.war
                        ├── servlet-1.0.war.md5
                        ├── servlet-1.0.war.sha1
                        ├── servlet-1.0.war.sha256
                        └── servlet-1.0.war.sha512

20 directories, 61 files
```
</details>

\
TODO: see also [SNAPSHOT case](https://central.sonatype.org/publish/publish-portal-snapshots/)

Testing with Maven 4.0.0-rc-4, there is no error message, but deployment does not happen: deploy plugin is removed, but the `central-publishing:publishing` goal does not get invoked.

## deploy with njord extension to Maven Resolver

https://maveniverse.eu/docs/njord/

    mvn clean deploy -DaltDeploymentRepository=id::njord:

With njord, `maven-deploy-plugin` stages the content to `njord:` url, which in fact stores content in `~/.njord/deploy-<counter>`:

<details><summary>mvn clean deploy -DaltDeploymentRepository=id::njord:</summary>

```
$ mvn clean deploy -DaltDeploymentRepository=id::njord:
[INFO] Scanning for projects...
[INFO] Njord 0.8.2 session created
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Build Order:
[INFO] 
[INFO] deploy study root                                                  [pom]
[INFO] sub projects                                                       [pom]
[INFO] logging                                                            [jar]
[INFO] core project classes                                               [jar]
[INFO] enterprise java beans                                              [ejb]
[INFO] servlet                                                            [war]
[INFO] ear assembly                                                       [ear]
[INFO] 
[INFO] ------------------< org.apache.maven.studies:deploy >-------------------
[INFO] Building deploy study root 1.0                                     [1/7]
[INFO]   from pom.xml
[INFO] --------------------------------[ pom ]---------------------------------
[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ deploy ---
[INFO] Deleting /home/herve/dev/maven/sources/studies/deploy/target
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ deploy ---
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/pom.xml to /home/herve/.m2/repository/org/apache/maven/studies/deploy/1.0/deploy-1.0.pom
[INFO] 
[INFO] --- deploy:3.1.4:deploy (default-deploy) @ deploy ---
[INFO] Using alternate deployment repository id::njord:
Uploading to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/deploy/1.0/deploy-1.0.pom
Uploaded to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/deploy/1.0/deploy-1.0.pom (6.3 kB at 1.0 MB/s)
Downloading from deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/deploy/maven-metadata.xml
Uploading to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/deploy/maven-metadata.xml
Uploaded to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/deploy/maven-metadata.xml (306 B at 306 kB/s)
[INFO] 
[INFO] -----------------< org.apache.maven.studies:projects >------------------
[INFO] Building sub projects 1.0                                          [2/7]
[INFO]   from projects/pom.xml
[INFO] --------------------------------[ pom ]---------------------------------
[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ projects ---
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ projects ---
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/pom.xml to /home/herve/.m2/repository/org/apache/maven/studies/projects/1.0/projects-1.0.pom
[INFO] 
[INFO] --- deploy:3.1.4:deploy (default-deploy) @ projects ---
[INFO] Using alternate deployment repository id::njord:
Uploading to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/projects/1.0/projects-1.0.pom
Uploaded to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/projects/1.0/projects-1.0.pom (696 B)
Downloading from deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/projects/maven-metadata.xml
Uploading to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/projects/maven-metadata.xml
Uploaded to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/projects/maven-metadata.xml (308 B)
[INFO] 
[INFO] ------------------< org.apache.maven.studies:logging >------------------
[INFO] Building logging 1.0                                               [3/7]
[INFO]   from projects/logging/pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ logging ---
[INFO] Deleting /home/herve/dev/maven/sources/studies/deploy/projects/logging/target
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ logging ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/logging/src/main/resources
[INFO] 
[INFO] --- compiler:3.14.0:compile (default-compile) @ logging ---
[INFO] No sources to compile
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ logging ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/logging/src/test/resources
[INFO] 
[INFO] --- compiler:3.14.0:testCompile (default-testCompile) @ logging ---
[INFO] No sources to compile
[INFO] 
[INFO] --- surefire:3.5.3:test (default-test) @ logging ---
[INFO] No tests to run.
[INFO] 
[INFO] --- jar:3.4.2:jar (default-jar) @ logging ---
[WARNING] JAR will be empty - no content was marked for inclusion!
[INFO] Building jar: /home/herve/dev/maven/sources/studies/deploy/projects/logging/target/logging-1.0.jar
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ logging ---
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/logging/pom.xml to /home/herve/.m2/repository/org/apache/maven/studies/logging/1.0/logging-1.0.pom
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/logging/target/logging-1.0.jar to /home/herve/.m2/repository/org/apache/maven/studies/logging/1.0/logging-1.0.jar
[INFO] 
[INFO] --- deploy:3.1.4:deploy (default-deploy) @ logging ---
[INFO] Using alternate deployment repository id::njord:
Uploading to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/logging/1.0/logging-1.0.pom
Uploaded to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/logging/1.0/logging-1.0.pom (492 B at 492 kB/s)
Uploading to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/logging/1.0/logging-1.0.jar
Uploaded to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/logging/1.0/logging-1.0.jar (1.5 kB at 726 kB/s)
Downloading from deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/logging/maven-metadata.xml
Uploading to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/logging/maven-metadata.xml
Uploaded to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/logging/maven-metadata.xml (307 B)
[INFO] 
[INFO] --------------< org.apache.maven.studies:primary-source >---------------
[INFO] Building core project classes 1.0                                  [4/7]
[INFO]   from projects/primary-source/pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ primary-source ---
[INFO] Deleting /home/herve/dev/maven/sources/studies/deploy/projects/primary-source/target
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ primary-source ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/primary-source/src/main/resources
[INFO] 
[INFO] --- compiler:3.14.0:compile (default-compile) @ primary-source ---
[INFO] No sources to compile
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ primary-source ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/primary-source/src/test/resources
[INFO] 
[INFO] --- compiler:3.14.0:testCompile (default-testCompile) @ primary-source ---
[INFO] No sources to compile
[INFO] 
[INFO] --- surefire:3.5.3:test (default-test) @ primary-source ---
[INFO] No tests to run.
[INFO] 
[INFO] --- jar:3.4.2:jar (default-jar) @ primary-source ---
[WARNING] JAR will be empty - no content was marked for inclusion!
[INFO] Building jar: /home/herve/dev/maven/sources/studies/deploy/projects/primary-source/target/primary-source-1.0.jar
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ primary-source ---
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/primary-source/pom.xml to /home/herve/.m2/repository/org/apache/maven/studies/primary-source/1.0/primary-source-1.0.pom
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/primary-source/target/primary-source-1.0.jar to /home/herve/.m2/repository/org/apache/maven/studies/primary-source/1.0/primary-source-1.0.jar
[INFO] 
[INFO] --- deploy:3.1.4:deploy (default-deploy) @ primary-source ---
[INFO] Using alternate deployment repository id::njord:
Uploading to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/primary-source/1.0/primary-source-1.0.pom
Uploaded to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/primary-source/1.0/primary-source-1.0.pom (1.0 kB at 1.0 MB/s)
Uploading to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/primary-source/1.0/primary-source-1.0.jar
Uploaded to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/primary-source/1.0/primary-source-1.0.jar (1.7 kB at 1.7 MB/s)
Downloading from deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/primary-source/maven-metadata.xml
Uploading to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/primary-source/maven-metadata.xml
Uploaded to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/primary-source/maven-metadata.xml (314 B)
[INFO] 
[INFO] -------------------< org.apache.maven.studies:ejbs >--------------------
[INFO] Building enterprise java beans 1.0                                 [5/7]
[INFO]   from projects/ejbs/pom.xml
[INFO] --------------------------------[ ejb ]---------------------------------
[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ ejbs ---
[INFO] Deleting /home/herve/dev/maven/sources/studies/deploy/projects/ejbs/target
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ ejbs ---
[INFO] Copying 1 resource from src/main/resources to target/classes
[INFO] 
[INFO] --- compiler:3.14.0:compile (default-compile) @ ejbs ---
[INFO] No sources to compile
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ ejbs ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/ejbs/src/test/resources
[INFO] 
[INFO] --- compiler:3.14.0:testCompile (default-testCompile) @ ejbs ---
[INFO] No sources to compile
[INFO] 
[INFO] --- surefire:3.5.3:test (default-test) @ ejbs ---
[INFO] No tests to run.
[INFO] 
[INFO] --- ejb:3.2.1:ejb (default-ejb) @ ejbs ---
[INFO] Building EJB ejbs-1.0 with EJB version 3.1
[INFO] Building jar: /home/herve/dev/maven/sources/studies/deploy/projects/ejbs/target/ejbs-1.0.jar
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ ejbs ---
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/ejbs/pom.xml to /home/herve/.m2/repository/org/apache/maven/studies/ejbs/1.0/ejbs-1.0.pom
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/ejbs/target/ejbs-1.0.jar to /home/herve/.m2/repository/org/apache/maven/studies/ejbs/1.0/ejbs-1.0.jar
[INFO] 
[INFO] --- deploy:3.1.4:deploy (default-deploy) @ ejbs ---
[INFO] Using alternate deployment repository id::njord:
Uploading to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/ejbs/1.0/ejbs-1.0.pom
Uploaded to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/ejbs/1.0/ejbs-1.0.pom (1.1 kB)
Uploading to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/ejbs/1.0/ejbs-1.0.jar
Uploaded to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/ejbs/1.0/ejbs-1.0.jar (1.8 kB at 878 kB/s)
Downloading from deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/ejbs/maven-metadata.xml
Uploading to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/ejbs/maven-metadata.xml
Uploaded to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/ejbs/maven-metadata.xml (304 B)
[INFO] 
[INFO] ------------------< org.apache.maven.studies:servlet >------------------
[INFO] Building servlet 1.0                                               [6/7]
[INFO]   from projects/servlet/pom.xml
[INFO] --------------------------------[ war ]---------------------------------
[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ servlet ---
[INFO] Deleting /home/herve/dev/maven/sources/studies/deploy/projects/servlet/target
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ servlet ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/servlet/src/main/resources
[INFO] 
[INFO] --- compiler:3.14.0:compile (default-compile) @ servlet ---
[INFO] No sources to compile
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ servlet ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/servlet/src/test/resources
[INFO] 
[INFO] --- compiler:3.14.0:testCompile (default-testCompile) @ servlet ---
[INFO] No sources to compile
[INFO] 
[INFO] --- surefire:3.5.3:test (default-test) @ servlet ---
[INFO] No tests to run.
[INFO] 
[INFO] --- war:3.4.0:war (default-war) @ servlet ---
[INFO] Packaging webapp
[INFO] Assembling webapp [servlet] in [/home/herve/dev/maven/sources/studies/deploy/projects/servlet/target/servlet-1.0]
[INFO] Processing war project
[INFO] Copying webapp resources [/home/herve/dev/maven/sources/studies/deploy/projects/servlet/src/main/webapp]
[INFO] Building war: /home/herve/dev/maven/sources/studies/deploy/projects/servlet/target/servlet-1.0.war
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ servlet ---
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/servlet/pom.xml to /home/herve/.m2/repository/org/apache/maven/studies/servlet/1.0/servlet-1.0.pom
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/servlet/target/servlet-1.0.war to /home/herve/.m2/repository/org/apache/maven/studies/servlet/1.0/servlet-1.0.war
[INFO] 
[INFO] --- deploy:3.1.4:deploy (default-deploy) @ servlet ---
[INFO] Using alternate deployment repository id::njord:
Uploading to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/servlet/1.0/servlet-1.0.pom
Uploaded to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/servlet/1.0/servlet-1.0.pom (718 B at 718 kB/s)
Uploading to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/servlet/1.0/servlet-1.0.war
Uploaded to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/servlet/1.0/servlet-1.0.war (1.5 kB at 1.5 MB/s)
Downloading from deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/servlet/maven-metadata.xml
Uploading to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/servlet/maven-metadata.xml
Uploaded to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/servlet/maven-metadata.xml (307 B at 307 kB/s)
[INFO] 
[INFO] --------------------< org.apache.maven.studies:ear >--------------------
[INFO] Building ear assembly 1.0                                          [7/7]
[INFO]   from projects/ear/pom.xml
[INFO] --------------------------------[ ear ]---------------------------------
[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ ear ---
[INFO] Deleting /home/herve/dev/maven/sources/studies/deploy/projects/ear/target
[INFO] 
[INFO] --- ear:3.4.0:generate-application-xml (default-generate-application-xml) @ ear ---
[INFO] Generating application.xml
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ ear ---
[INFO] skip non existing resourceDirectory /home/herve/dev/maven/sources/studies/deploy/projects/ear/src/main/resources
[INFO] 
[INFO] --- ear:3.4.0:ear (default-ear) @ ear ---
[INFO] Copying artifact [ejb:org.apache.maven.studies:ejbs:1.0] to [org.apache.maven.studies-ejbs-1.0.jar]
[INFO] Copying artifact [war:org.apache.maven.studies:servlet:1.0] to [org.apache.maven.studies-servlet-1.0.war]
[INFO] Copying artifact [jar:org.apache.maven.studies:primary-source:1.0] to [org.apache.maven.studies-primary-source-1.0.jar]
[INFO] Copying artifact [jar:org.apache.maven.studies:logging:1.0] to [org.apache.maven.studies-logging-1.0.jar]
[INFO] Building ear: /home/herve/dev/maven/sources/studies/deploy/projects/ear/target/ear-1.0.ear
[INFO] 
[INFO] --- install:3.1.4:install (default-install) @ ear ---
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/ear/pom.xml to /home/herve/.m2/repository/org/apache/maven/studies/ear/1.0/ear-1.0.pom
[INFO] Installing /home/herve/dev/maven/sources/studies/deploy/projects/ear/target/ear-1.0.ear to /home/herve/.m2/repository/org/apache/maven/studies/ear/1.0/ear-1.0.ear
[INFO] 
[INFO] --- deploy:3.1.4:deploy (default-deploy) @ ear ---
[INFO] Using alternate deployment repository id::njord:
Uploading to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/ear/1.0/ear-1.0.pom
Uploaded to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/ear/1.0/ear-1.0.pom (1.4 kB at 1.4 MB/s)
Uploading to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/ear/1.0/ear-1.0.ear
Uploaded to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/ear/1.0/ear-1.0.ear (6.2 kB at 6.2 MB/s)
Downloading from deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/ear/maven-metadata.xml
Uploading to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/ear/maven-metadata.xml
Uploaded to deploy-00007: file:///home/herve/.njord/deploy-00007/org/apache/maven/studies/ear/maven-metadata.xml (303 B at 303 kB/s)
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for deploy study root 1.0:
[INFO] 
[INFO] deploy study root .................................. SUCCESS [  0.105 s]
[INFO] sub projects ....................................... SUCCESS [  0.006 s]
[INFO] logging ............................................ SUCCESS [  0.285 s]
[INFO] core project classes ............................... SUCCESS [  0.021 s]
[INFO] enterprise java beans .............................. SUCCESS [  0.157 s]
[INFO] servlet ............................................ SUCCESS [  0.134 s]
[INFO] ear assembly ....................................... SUCCESS [  0.118 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.003 s
[INFO] Finished at: 2025-07-19T14:33:20+02:00
[INFO] ------------------------------------------------------------------------
[INFO] Njord session closed
```
</details>

    tree ~/.njord/deploy-*

<details><summary>tree ~/.njord/deploy-*</summary>

```
$ tree ~/.njord/deploy-00007
/home/herve/.njord/deploy-00007
└── org
    └── apache
        └── maven
            └── studies
                ├── deploy
                │   ├── 1.0
                │   │   ├── deploy-1.0.pom
                │   │   ├── deploy-1.0.pom.md5
                │   │   ├── deploy-1.0.pom.sha1
                │   │   ├── deploy-1.0.pom.sha256
                │   │   └── deploy-1.0.pom.sha512
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   ├── maven-metadata.xml.sha1
                │   ├── maven-metadata.xml.sha256
                │   └── maven-metadata.xml.sha512
                ├── ear
                │   ├── 1.0
                │   │   ├── ear-1.0.ear
                │   │   ├── ear-1.0.ear.md5
                │   │   ├── ear-1.0.ear.sha1
                │   │   ├── ear-1.0.ear.sha256
                │   │   ├── ear-1.0.ear.sha512
                │   │   ├── ear-1.0.pom
                │   │   ├── ear-1.0.pom.md5
                │   │   ├── ear-1.0.pom.sha1
                │   │   ├── ear-1.0.pom.sha256
                │   │   └── ear-1.0.pom.sha512
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   ├── maven-metadata.xml.sha1
                │   ├── maven-metadata.xml.sha256
                │   └── maven-metadata.xml.sha512
                ├── ejbs
                │   ├── 1.0
                │   │   ├── ejbs-1.0.jar
                │   │   ├── ejbs-1.0.jar.md5
                │   │   ├── ejbs-1.0.jar.sha1
                │   │   ├── ejbs-1.0.jar.sha256
                │   │   ├── ejbs-1.0.jar.sha512
                │   │   ├── ejbs-1.0.pom
                │   │   ├── ejbs-1.0.pom.md5
                │   │   ├── ejbs-1.0.pom.sha1
                │   │   ├── ejbs-1.0.pom.sha256
                │   │   └── ejbs-1.0.pom.sha512
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   ├── maven-metadata.xml.sha1
                │   ├── maven-metadata.xml.sha256
                │   └── maven-metadata.xml.sha512
                ├── logging
                │   ├── 1.0
                │   │   ├── logging-1.0.jar
                │   │   ├── logging-1.0.jar.md5
                │   │   ├── logging-1.0.jar.sha1
                │   │   ├── logging-1.0.jar.sha256
                │   │   ├── logging-1.0.jar.sha512
                │   │   ├── logging-1.0.pom
                │   │   ├── logging-1.0.pom.md5
                │   │   ├── logging-1.0.pom.sha1
                │   │   ├── logging-1.0.pom.sha256
                │   │   └── logging-1.0.pom.sha512
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   ├── maven-metadata.xml.sha1
                │   ├── maven-metadata.xml.sha256
                │   └── maven-metadata.xml.sha512
                ├── primary-source
                │   ├── 1.0
                │   │   ├── primary-source-1.0.jar
                │   │   ├── primary-source-1.0.jar.md5
                │   │   ├── primary-source-1.0.jar.sha1
                │   │   ├── primary-source-1.0.jar.sha256
                │   │   ├── primary-source-1.0.jar.sha512
                │   │   ├── primary-source-1.0.pom
                │   │   ├── primary-source-1.0.pom.md5
                │   │   ├── primary-source-1.0.pom.sha1
                │   │   ├── primary-source-1.0.pom.sha256
                │   │   └── primary-source-1.0.pom.sha512
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   ├── maven-metadata.xml.sha1
                │   ├── maven-metadata.xml.sha256
                │   └── maven-metadata.xml.sha512
                ├── projects
                │   ├── 1.0
                │   │   ├── projects-1.0.pom
                │   │   ├── projects-1.0.pom.md5
                │   │   ├── projects-1.0.pom.sha1
                │   │   ├── projects-1.0.pom.sha256
                │   │   └── projects-1.0.pom.sha512
                │   ├── maven-metadata.xml
                │   ├── maven-metadata.xml.md5
                │   ├── maven-metadata.xml.sha1
                │   ├── maven-metadata.xml.sha256
                │   └── maven-metadata.xml.sha512
                └── servlet
                    ├── 1.0
                    │   ├── servlet-1.0.pom
                    │   ├── servlet-1.0.pom.md5
                    │   ├── servlet-1.0.pom.sha1
                    │   ├── servlet-1.0.pom.sha256
                    │   ├── servlet-1.0.pom.sha512
                    │   ├── servlet-1.0.war
                    │   ├── servlet-1.0.war.md5
                    │   ├── servlet-1.0.war.sha1
                    │   ├── servlet-1.0.war.sha256
                    │   └── servlet-1.0.war.sha512
                    ├── maven-metadata.xml
                    ├── maven-metadata.xml.md5
                    ├── maven-metadata.xml.sha1
                    ├── maven-metadata.xml.sha256
                    └── maven-metadata.xml.sha512

19 directories, 95 files
```
</details>

\
And at Maven build end of execution (through [a MavenLifecycleParticipant](https://github.com/maveniverse/njord/blob/main/extension3/src/main/java/eu/maveniverse/maven/njord/extension3/NjordSessionLifecycleParticipant.java)), after `BUILD SUCCESS`, the staged content is eventually uploaded to Maven Central Portal upload API, or can be manipulated with [`njord-maven-plugin` provided goals](https://maveniverse.eu/docs/njord/plugin-documentation/plugin-info.html):

    mvn -Pnjord -N njord:list

<details><summary> mvn -Pnjord -N njord:list</summary>

```
$ mvn -Pnjord -N njord:list
[INFO] Scanning for projects...
[INFO] Njord 0.8.2 session created
[INFO] 
[INFO] ------------------< org.apache.maven.studies:deploy >-------------------
[INFO] Building deploy study root 1.0
[INFO]   from pom.xml
[INFO] --------------------------------[ pom ]---------------------------------
[INFO] 
[INFO] --- njord:0.8.2:list (default-cli) @ deploy ---
[INFO] List of existing ArtifactStore:
[INFO] - deploy-00001 from org.apache.maven.studies:deploy:pom:1.0 (2025-07-15T17:49:35.596Z, RELEASE, release-sca, 13 artifacts)
[INFO] - deploy-00002 from org.apache.maven.studies:deploy:pom:1.0 (2025-07-15T17:53:24.149Z, RELEASE, release-sca, 13 artifacts)
[INFO] - deploy-00003 from org.apache.maven.studies:deploy:pom:1.0 (2025-07-15T17:53:41.127Z, RELEASE, release-sca, 13 artifacts)
[INFO] - deploy-00004 from org.apache.maven.studies:deploy:pom:1.0 (2025-07-15T17:54:08.542Z, RELEASE, release-sca, 13 artifacts)
[INFO] - deploy-00005 from org.apache.maven.studies:deploy:pom:1.0 (2025-07-15T17:54:24.314Z, RELEASE, release-sca, 13 artifacts)
[INFO] - deploy-00006 from org.apache.maven.studies:deploy:pom:1.0 (2025-07-17T01:17:10.607Z, RELEASE, release-sca, 13 artifacts)
[INFO] - deploy-00007 from org.apache.maven.studies:deploy:pom:1.0 (2025-07-19T12:33:19.475Z, RELEASE, release-sca, 12 artifacts)
[INFO] Total of 7 ArtifactStore.
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.296 s
[INFO] Finished at: 2025-07-19T14:36:13+02:00
[INFO] ------------------------------------------------------------------------
[INFO] Njord session closed
```
</details>

TODO: dig into the upload aspects explained in [Using it](https://maveniverse.eu/docs/njord/using-it/) documentation...

Running with Maven 4 gives exact same result.

## deploy with [JReleaser](https://jreleaser.org/)

TODO supports [Publishing to Maven Central](https://jreleaser.org/guide/latest/examples/maven/maven-central.html)
