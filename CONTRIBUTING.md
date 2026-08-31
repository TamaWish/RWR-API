# Contributing to RWR-API

RWR-API is the stable public integration boundary for ResourceWorldResetter. Changes should keep the
surface small, immutable, provider-neutral, and safe for concurrent reads.

## Development setup

- JDK 21 or newer
- Maven 3.9 or newer
- Git

Run the complete local gate with:

```shell
mvn clean verify
```

This compiles with Java 21 bytecode, runs contract tests, builds binary/source/Javadoc JARs with full
Javadoc validation, and fails on dependency-analysis warnings.

## Compatibility rules

- Do not remove or rename a public type, method, record component, enum constant, or package during 5.x.
- Additive methods and event types are allowed in a minor or patch release when existing consumers
  continue to link and run.
- Treat adding enum constants carefully and document them; consumers are advised to retain a default
  branch.
- Do not expose RWR implementation classes, Multiverse objects, Worlds objects, mutable collections,
  configuration mutation, or reset-control operations.
- Keep all public types under `io.github.tamawish.rwr.api`.
- Document public behavior and add or update contract tests for every API change.

## Pull requests

Describe the use case, compatibility impact, tests performed, and related RWR runtime change. Never
include credentials, signing keys, Maven Central tokens, server data, or private webhook URLs.
