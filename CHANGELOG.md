## [2.3.1] - 2022-04
- fix: adjust max file size to the correct size of 1MB (previously: 4000 bytes)
- chore: update upload attemps to 10

## [2.3.0] - 2022-03
- fix: make 5 consequent attempts to getAnalysis during polling if operation does not succeed with 404
- fix: make 5 attempts to re-upload files if operation does not succeed
- fix: do not try to getAnalysis if `upload files` is not succeed (i.e. `missingFiles` is not empty after uploads)
- fix: avoid remove operation for empty immutable List
- fix: check file in marker for nullability before proceed
- fix: internal ConcurrentModificationException 
- feat: provide unique (per project) `shard` to getAnalysis call
- feat: provide `analysisContext` key (`getAnalysis` request) for better tracking/logging on backend
- chore: reshape/refactor REST API wrapper to be replaceable through constructor base DI

## [2.2.1] - 2021-12-10
- fix: don't upload empty files

## [2.2.0] - 2020-11-29
- feat: update to latest snyk-code api

## [2.1.12] - 2020-10-17
- fix: isFullRescanRequested() should be False after rescan finished and before UI updates.

## [2.1.11] - 2020-10-05
- added param: "waiting results" timeout. 

## [2.0.18] - 2020-10-05
- fix exception when Null markers received. 

## [2.0.16] - 2020-09-08
- fix inner caches cleanup; update modes compatibility. 

## [2.0.15] - 2020-09-08
- fix .ignore files parsing

## [2.0.0] - 2020-06-20
- Common logic moved to java-client from jetbrains-plugin

## [1.0.0] - 2020-06-20
- Final major release for 1.x version branch before switching to 2.x

## [0.0.13] - 2020-05-27
- `Check Bundle` API function added

## [0.0.12] - 2020-05-22
- Added support for Java 8, required for Android Studio

## [0.0.11] - 2020-05-19
### Added
- Initial release
