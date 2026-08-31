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
7. Review Central Portal validation. The workflow deliberately stages with `autoPublish=false`; use
   the Portal's Publish action only after validation and artifact inspection.
8. Wait until `io.github.tamawish:rwr-api:<version>` resolves from Maven Central in a clean cache.
9. Tag the reviewed commit as `v<version>`, create GitHub release notes, and then release dependent
   RWR projects.

If Central validation fails, drop the staged deployment, correct the project, and stage a new bundle.
Never attempt to overwrite a published version.
