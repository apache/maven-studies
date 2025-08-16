# Git Branch Protection at ASF

See also [Wiki](https://cwiki.apache.org/confluence/display/MAVEN/Branch+Protection).

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

## With GitHub protected branch with default rules: `{ }`

Force push protection is now activated in addition, and on all configured additional branches.
<details><summary>git commit --amend && git push --force</summary>

```
$ git commit --amend
[master 88548e931] enable branch protection
 Date: Sat Aug 16 11:57:40 2025 +0100
 1 file changed, 12 insertions(+)
❯ git push --force
Enumerating objects: 5, done.
Counting objects: 100% (5/5), done.
Delta compression using up to 14 threads
Compressing objects: 100% (3/3), done.
Writing objects: 100% (3/3), 524 bytes | 524.00 KiB/s, done.
Total 3 (delta 0), reused 0 (delta 0), pack-reused 0
remote: error: GH006: Protected branch update failed for refs/heads/master.
remote:
remote: - Cannot force-push to this branch
To https://github.com/apache/maven-studies.git
 ! [remote rejected]     master -> master (protected branch hook declined)
error: failed to push some refs to 'https://github.com/apache/maven-studies.git'

$ git push --force gitbox
Enumerating objects: 5, done.
Counting objects: 100% (5/5), done.
Delta compression using up to 14 threads
Compressing objects: 100% (3/3), done.
Writing objects: 100% (3/3), 524 bytes | 524.00 KiB/s, done.
Total 3 (delta 0), reused 0 (delta 0), pack-reused 0
remote: Rewinding refs/heads/master is forbidden.
remote:
To https://gitbox.apache.org/repos/asf/maven-studies.git
 ! [remote rejected]     master -> master (pre-receive hook declined)
error: failed to push some refs to 'https://gitbox.apache.org/repos/asf/maven-studies.git'
```
</details>

