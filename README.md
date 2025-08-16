# Maven Studies (former sandbox)

This repository contains studies on new ideas for Maven:
it's a replacement for Subversion times' [Maven Sandbox](https://github.com/apache/maven-sandbox)
with an organization optimized for Git, using orphaned branches instead of directories.

Notice: in the past, it required infra to create a Git repository, but self service was later provided. What remains not self service is Git repository deletion (not even sure it is accepted) or archival.

Each study is managed in a separate [Git orphan branch](https://git-scm.com/docs/git-checkout#git-checkout---orphanltnewbranchgt).

## Creating a new Maven Study

```
git checkout --orphan my-new-study
rm .git/index
git clean -fdx
```

## Publishing Site for a Maven Study

Studies can publish a site to https://maven.apache.org/studies/
