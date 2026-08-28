# Changelog

## 5.1.0 - Unreleased

- Add the stable `RwrApi` read-only service contract.
- Add immutable managed-world and reset-status snapshots.
- Add API-owned reset lifecycle, failure, safety, and operational-state enums.
- Publish cancellable pre-reset and terminal post-reset Bukkit events.
- Use provider-neutral public failure names across Multiverse and Worlds runtimes.
- Deprecate the legacy `getMultiverseWorld()` event aliases in favor of `getWorldName()`.

Publishing remains blocked until the matching ResourceWorldResetter 5.1.0 runtime integration ships.
