# Maven Studies

This repository contains studies on new ideas for Maven:
it's a replacement for [Maven Sandbox](https://github.com/apache/maven-sandbox)
with an organization optimized for Git.

Each study is managed in a separate [Git orphan branch](https://git-scm.com/docs/git-checkout#git-checkout---orphanltnewbranchgt).

## Creating a new Maven Study

```
git checkout --orphan my-new-study
rm .git/index
git clean -fdx
```
