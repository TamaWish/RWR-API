# ResourceWorldResetter API

The stable, read-only integration contract for ResourceWorldResetter 5.1.0 and newer.

> **Release gate:** `5.1.0` must not be published until a matching RWR runtime release registers the
> service and emits these API-owned events.

## Dependency

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

RWR API classes are supplied by the running RWR plugin. Do not shade this artifact into a consumer
plugin. Consumers also need their normal `compileOnly`/`provided` Bukkit or Paper API dependency.

## Service lookup

```java
RwrApi.find(getServer()).ifPresentOrElse(api -> {
    for (ManagedWorldSnapshot world : api.managedWorlds()) {
        getLogger().info(world.id() + " -> " + world.state());
    }
}, () -> getLogger().warning("RWR is unavailable or not ready"));
```

The service exists only after RWR enables successfully and is removed when RWR disables. Use the
Bukkit services manager rather than casting RWR's plugin main class. Declare the appropriate runtime
plugin name in `depend` when integration is mandatory, or `softdepend` and handle an absent service:

- Spigot: `ResourceWorldResetter`
- Paper/Folia: `ResourceWorldResetter-Paper-Folia`

World IDs are matched case-insensitively. Returned records and lists are immutable point-in-time
snapshots. Calls are safe for concurrent reads, but consumers must still follow Bukkit's threading
rules for their own server operations.

## Events

Listen for `ResourceWorldPreResetEvent` to observe or cancel a reset before it starts. Every accepted
attempt reaches a terminal `ResourceWorldPostResetEvent`, including event cancellation and failures.

The API intentionally does not expose reset commands, mutable configuration, scheduler control,
history storage, Multiverse-Core, or TheNextLvl Worlds objects.

## Compatibility

Public classes, methods, constructors, enum constants, and their documented semantics remain
compatible throughout RWR 5.x. Additions may occur in minor releases. Incompatible changes require
RWR 6.0. Internal runtime packages have no compatibility guarantee.

## Build

Requires Java 21 and Maven 3.9 or newer:

```shell
mvn verify
```

The `central-release` profile signs and uploads artifacts. It is reserved for the gated release
workflow, requires Maven Central plus GPG credentials, and refuses to run unless the matching runtime
release is explicitly confirmed with `-Drwr.runtime.api.ready=true`.
