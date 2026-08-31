# RWR-API Release Procedure

Maven Central releases are immutable. Perform these steps from a reviewed, clean `main` branch.

1. Confirm the POM version, README dependency examples, changelog version/date, and RWR runtime API
   version all agree.
2. Run `mvn clean verify` with JDK 21.
3. Build RWR, RWR-Discord Webhook, and RWR-PlaceholderAPI against this exact API version.
4. Confirm the GitHub Actions `CI` workflow succeeds on `main`.
5. Confirm the `maven-central` environment contains `CENTRAL_USERNAME`, `CENTRAL_TOKEN`,
   `GPG_PRIVATE_KEY`, and `GPG_PASSPHRASE` secrets. Never store their values in the repository.
6. Run the `Stage on Maven Central` workflow and check the contract-ready confirmation.
7. Copy the deployment ID printed by that exact workflow run. In Central Portal, open that deployment
   ID and confirm it contains only `io.github.tamawish:rwr-api:<version>`. Do not publish a similarly
   named or older deployment.
8. Review Central Portal validation and inspect the staged JAR/sources for the
   `io.github.tamawish.rwr.api` namespace. The workflow deliberately stages with `autoPublish=false`;
   use the Portal's Publish action only after these checks.
9. Wait until `io.github.tamawish:rwr-api:<version>` resolves from Maven Central in a clean cache.
10. Download the public binary and sources JARs and verify they contain
   `io/github/tamawish/rwr/api/RwrApi` and no `com/lozaine` API package.
11. Tag the reviewed commit as `v<version>`, create GitHub release notes, and then release dependent
   RWR projects.

If Central validation fails, drop the staged deployment, correct the project, and stage a new bundle.
Never attempt to overwrite a published version.
