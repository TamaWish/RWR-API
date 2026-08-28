# Changelog

## 5.1.0 - Unreleased

- Add the stable `RwrApi` read-only service contract.
- Add immutable managed-world and reset-status snapshots.
- Add API-owned reset lifecycle, failure, safety, and operational-state enums.
- Publish cancellable pre-reset and terminal post-reset Bukkit events.
- Use provider-neutral public failure names across Multiverse and Worlds runtimes.
- Deprecate the legacy `getMultiverseWorld()` event aliases in favor of `getWorldName()`.

> **Note:** This version will be published to Maven only when the matching ResourceWorldResetter 5.1.0 runtime (Spigot + Paper/Folia) that registers the service and emits these events is also released.
