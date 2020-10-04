# PGP sign plugin 

Create PGP signature for all artifacts in maven project

## Assumptions
 - first version require maven 3.7.0-SNAPSHOT
 - use build/consumer feature
 
## TODO
 - documentations
 - check if all artifacts are ready to sign - if package goal was running
 - verify password encryption in settings.xml
 - detect expired key
 - signing by sub key
 - review it test from GPG plugin - maybe some case should be addressed
 - decision - if we should move pgp code to external project
