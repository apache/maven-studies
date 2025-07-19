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
