# Changelog

Public **5.x** history for rwr-api. All 5.x.x versions stay in this file. When 6.x begins, start `CHANGELOG_v6.md`.

## 5.1.1 - 2026-09-01

- Add `ResourceWorldResetWarningEvent` for configured scheduled-reset warnings.
- Establish `io.github.tamawish.rwr.api` as the first public package and Maven contract.
- Publish immutable world/status snapshots and reset lifecycle events for RWR 5.1.0.

This is the first public Maven Central release. The earlier 5.1.0 build was an unpublished development
preview under `com.lozaine.resourceworldresetter.api`; developers must compile against 5.1.1 and use
the `io.github.tamawish.rwr.api` packages shown in the README and Javadocs.

## 5.1.0 - Unpublished preview

- Add the stable `RwrApi` read-only service contract.
- Add immutable managed-world and reset-status snapshots.
- Add API-owned reset lifecycle, failure, safety, and operational-state enums.
- Publish cancellable pre-reset and terminal post-reset Bukkit events.
- Use provider-neutral public failure names across Multiverse and Worlds runtimes.
- Deprecate the legacy `getMultiverseWorld()` event aliases in favor of `getWorldName()`.
