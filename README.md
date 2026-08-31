# ResourceWorldResetter API

The public, read-only integration API for ResourceWorldResetter 5.1.0 and newer. It lets Bukkit,
Spigot, Paper, Purpur, and Folia plugins inspect RWR-managed worlds and reset activity without
depending on RWR's internal implementation or world-provider APIs.

Requires Java 21 and ResourceWorldResetter 5.1.0 or newer on the server. Version 5.1.1 is the first
public API release; use only the `io.github.tamawish.rwr.api` packages.

## Add the dependency

Maven:

```xml
<dependency>
  <groupId>io.github.tamawish</groupId>
  <artifactId>rwr-api</artifactId>
  <version>5.1.1</version>
  <scope>provided</scope>
</dependency>
```

Gradle:

```kotlin
dependencies {
    compileOnly("io.github.tamawish:rwr-api:5.1.1")
}
```

RWR supplies the API classes at runtime. Do not shade `rwr-api` into your plugin and do not install a
separate API JAR on the server. Keep your Bukkit or Paper API dependency as `provided`/`compileOnly` too.

## Find the service

RWR registers `RwrApi` through Bukkit's `ServicesManager` after it enables successfully:

```java
RwrApi.find(getServer()).ifPresentOrElse(api -> {
    for (ManagedWorldSnapshot world : api.managedWorlds()) {
        getLogger().info(world.id() + " -> " + world.state());
    }
}, () -> getLogger().warning("RWR is unavailable or not ready"));
```

Use `depend` when RWR is required, or `softdepend` and handle an unavailable service when integration
is optional. The runtime plugin names are:

- Spigot and CraftBukkit: `ResourceWorldResetter`
- Paper, Purpur, and Folia: `ResourceWorldResetter-Paper-Folia`

The service is absent when RWR fails to start and is removed when RWR disables.

Do not retain the `RwrApi` service instance across RWR disable, reload, or plugin-manager lifecycle
events. Call `RwrApi.find(server)` again when your integration needs to reconnect. Snapshot methods
support concurrent reads; returned values and lists are immutable.

## Available information

The API provides:

- immutable snapshots of managed worlds, including RWR ID, platform world name, display name,
  operational state, and reset capability;
- reset status containing the world, operation ID, phase, message, and active/terminal helpers;
- case-insensitive lookup by RWR world ID;
- `ResourceWorldResetWarningEvent`, emitted for each configured scheduled-reset warning;
- `ResourceWorldPreResetEvent`, which can cancel a reset before it starts;
- `ResourceWorldPostResetEvent`, which reports successful, failed, cancelled, and interrupted outcomes.

API 5.1.0 contains snapshots, status, and pre/post lifecycle events. Scheduled warning events were
added compatibly in API 5.1.1; integrations listening for warnings must compile against 5.1.1.

Warning events contain the RWR world ID, platform world name, whole minutes remaining, and scheduled
reset `Instant`. They do not have an operation ID because RWR allocates one only when a reset begins.
A warned reset may not run if its schedule or world configuration changes afterward.

```java
@EventHandler
public void onResetWarning(ResourceWorldResetWarningEvent event) {
    getLogger().info(event.getWorldName() + " resets in " + event.getMinutesRemaining() + " minutes");
}
```

To cancel a reset before RWR begins evacuation or regeneration:

```java
@EventHandler
public void onPreReset(ResourceWorldPreResetEvent event) {
    if (maintenanceMode) {
        event.setCancelled(true);
    }
}
```

Listen at `EventPriority.MONITOR` when observing outcomes without changing them. A post-reset event is
terminal and exposes `phase`, optional `failure`, retry `safety`, and a diagnostic message. Consumers
should branch on enum values rather than parsing the human-readable message.

Returned records and collections are immutable point-in-time snapshots. API queries support concurrent
reads, but plugins must still follow Bukkit's threading rules for any server operations they perform.

The API does not expose reset triggering, configuration mutation, reset history, scheduler control,
Multiverse-Core objects, or Worlds objects.

## Compatibility

The public compatibility baseline begins at API 5.1.1. The public API remains compatible throughout
RWR 5.x. Minor releases may add methods, event types, or enum constants; consumers should include a
default branch when switching over enums. Removals, package moves, or incompatible signature changes
are reserved for RWR 6.0.

## Developer resources

- Generated source and Javadoc JARs are published beside the binary on Maven Central.
- [`RwrApiConsumer`](src/test/java/example/RwrApiConsumer.java) is a compact service-and-event example.
- [`CONTRIBUTING.md`](CONTRIBUTING.md) describes local verification and compatibility expectations.
- Security issues should follow [`SECURITY.md`](SECURITY.md), not public issue reports.
- Maintainer publication steps are recorded in [`RELEASING.md`](RELEASING.md).

Licensed under the [BSD 3-Clause License](LICENSE).
