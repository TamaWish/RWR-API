# ResourceWorldResetter API

The public, read-only integration API for ResourceWorldResetter 5.1.0 and newer. It lets Bukkit,
Spigot, Paper, Purpur, and Folia plugins inspect RWR-managed worlds and reset activity without
depending on RWR's internal implementation or world-provider APIs.

Requires Java 21 and a matching RWR runtime version on the server.

## Add the dependency

Maven:

```xml
<dependency>
  <groupId>io.github.tamawish</groupId>
  <artifactId>rwr-api</artifactId>
  <version>5.1.0</version>
  <scope>provided</scope>
</dependency>
```

Gradle:

```kotlin
dependencies {
    compileOnly("io.github.tamawish:rwr-api:5.1.0")
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

## Available information

The API provides:

- immutable snapshots of managed worlds, including RWR ID, platform world name, display name,
  operational state, and reset capability;
- reset status containing the world, operation ID, phase, message, and active/terminal helpers;
- case-insensitive lookup by RWR world ID;
- `ResourceWorldPreResetEvent`, which can cancel a reset before it starts;
- `ResourceWorldPostResetEvent`, which reports successful, failed, cancelled, and interrupted outcomes.

Returned records and collections are immutable point-in-time snapshots. API queries support concurrent
reads, but plugins must still follow Bukkit's threading rules for any server operations they perform.

The API does not expose reset triggering, configuration mutation, reset history, scheduler control,
Multiverse-Core objects, or Worlds objects.

## Compatibility

The public API remains compatible throughout RWR 5.x. Minor releases may add new functionality;
removals or incompatible signature changes are reserved for RWR 6.0.

Licensed under the [BSD 3-Clause License](LICENSE).
