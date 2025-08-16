# Maven Studies (former sandbox)

This repository contains studies on new ideas for Maven:
it's a replacement for [Maven Sandbox](https://github.com/apache/maven-sandbox)
with an organization optimized for Git (avoid temporary Git repositories creation, that are not easy to delete at ASF but will be archived).

Each study is managed in a separate [Git orphan branch](https://git-scm.com/docs/git-checkout#git-checkout---orphanltnewbranchgt).

## Creating a new Maven Study

```
git checkout --orphan my-new-study
rm .git/index
git clean -fdx
```

## Publishing Site for a Maven Study

Studies can publish a site to https://maven.apache.org/studies/
