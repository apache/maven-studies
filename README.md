# Git Branch Protection at ASF

## Default

Test on a repository without anything configured shows:
- `master` is protected against branch deletion, be it through GitHub or GitBox
<details><summary>git push origin --delete master / git push gitbox --delete master</summary>


```
$ git push origin --delete master
To https://github.com/apache/maven-studies.git
 ! [remote rejected]     master (refusing to delete the current branch: refs/heads/master)
error: failed to push some refs to 'https://github.com/apache/maven-studies.git'

$ git push gitbox --delete master
remote: Rewinding refs/heads/master is forbidden.
remote:
To https://gitbox.apache.org/repos/asf/maven-studies.git
 ! [remote rejected]     master (pre-receive hook declined)
error: failed to push some refs to 'https://gitbox.apache.org/repos/asf/maven-studies.git'
```
</details>

- `master` is not protected against force push
<details><summary>git commit --amend && git push --force</summary>

```
$ git commit --amend
[master 7afcc9bc5] add link to studies sites
 Date: Sat Aug 16 11:07:08 2025 +0100
 1 file changed, 6 insertions(+), 2 deletions(-)
$ git push --force
Enumerating objects: 5, done.
Counting objects: 100% (5/5), done.
Delta compression using up to 14 threads
Compressing objects: 100% (3/3), done.
Writing objects: 100% (3/3), 509 bytes | 509.00 KiB/s, done.
Total 3 (delta 1), reused 0 (delta 0), pack-reused 0
remote: Resolving deltas: 100% (1/1), completed with 1 local object.
To https://github.com/apache/maven-studies.git
 + 77d4e2833...7afcc9bc5 master -> master (forced update)
 ```
 </details>
