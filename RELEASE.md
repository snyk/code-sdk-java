# Release

1. [Create release](https://github.com/snyk/code-sdk-java/releases) in github with a new tag vX.X.X for the version
2. Add changes & fixes to the release information
3. Wait for the release action to run
4. Log into [Sonatype](https://oss.sonatype.org)
5. Click on [Staging Repositories](https://oss.sonatype.org/#)
6. Select `iosnyk-xxxx`
7. Click on `Close`
8. Wait for the Close activity to finish (takes about 10 min)
9. Select `iosnyk-xxxx` staging repository again
10. Click on `Release` (takes about 10 min)

See [Staging Overview](https://help.sonatype.com/repomanager2/staging-releases/staging-overview) for 
some general Sonatype info.
