# JDBC Driver Setup

This project does not declare the PostgreSQL JDBC dependency directly as `org.postgresql:postgresql` in `pom.xml`.

Instead, it depends on a local Maven artifact with these coordinates:

- `groupId`: `at.tailor`
- `artifactId`: `driver`
- `version`: `1.0.0`

The purpose of this alias is to let the application depend on a stable internal coordinate while the actual JDBC driver jar can be swapped underneath it.

## How it works

`pom.xml` includes:

```xml
<dependency>
    <groupId>at.tailor</groupId>
    <artifactId>driver</artifactId>
    <version>1.0.0</version>
    <scope>provided</scope>
</dependency>
```

At build time, the Dockerfile downloads the real PostgreSQL JDBC jar and re-installs it into the local Maven repository under the alias `at.tailor:driver:1.0.0`.

Relevant Dockerfile steps:

```dockerfile
RUN mvn dependency:get -DgroupId=org.postgresql -DartifactId=postgresql -Dversion=42.5.3
RUN export groupFolder=$(echo "org.postgresql" | sed 's/\./\//g'); mvn install:install-file -Dfile=/root/.m2/repository/$groupFolder/postgresql/42.5.3/postgresql-42.5.3.jar -DgroupId=at.tailor -DartifactId=driver -Dversion=1.0.0 -Dpackaging=jar
```

That means the jar on the classpath is actually the PostgreSQL driver jar, but Maven sees it as `at.tailor:driver:1.0.0`.

## Why the application can use PostgreSQL

Spring is configured to load this driver class:

- `org.postgresql.Driver`

The datasource settings are supplied via environment variables in `docker-compose.yml` and read from `src/main/resources/application.properties`.

As long as the aliased dependency resolves to a jar containing `org.postgresql.Driver`, startup succeeds.

## Important constraint

This setup requires the alias artifact to exist in the local Maven repository of the build environment.

If `at.tailor:driver:1.0.0` has not been installed locally, Maven cannot resolve it from Maven Central, because it is not a public artifact.

Typical symptom:

```text
Could not resolve dependencies for project at.tailor:gamevote-api:jar:0.0.1-SNAPSHOT:
at.tailor:driver:jar:1.0.0 was not found
```

## Regression that happened on March 29, 2026

The dependency was moved from the main dependency list into a Maven profile named `custom-driver`.

That changed behavior in an important way:

- before: the aliased driver dependency was always present in the default build
- after: it was only present when Maven was run with `-Pcustom-driver`

Because the application startup expects the PostgreSQL driver to be on the classpath by default, moving the dependency into a profile caused startup failures when that profile was not active.

The fix was to move `at.tailor:driver:1.0.0` back into the main `<dependencies>` section.

## Practical guidance

- Keep `at.tailor:driver:1.0.0` in the default dependencies if PostgreSQL is required for normal startup.
- If you want profile-based driver selection, every runtime path must explicitly activate the correct profile.
- If you want a simpler setup, replace the alias dependency with a direct dependency on `org.postgresql:postgresql`.

## Critique

This approach is technically workable, but for this project it is a poor tradeoff.

Main issues:

- It hides the real runtime dependency. The service requires PostgreSQL, but the build file does not say that directly.
- It depends on local Maven repository state, which makes fresh-machine setup and CI behavior more fragile.
- It reduces build reproducibility because Docker, local Maven, IDE imports, and CI can resolve dependencies differently.
- It makes failures harder to diagnose because the visible error often looks unrelated to the real mechanism.
- It bypasses normal Maven and Spring Boot conventions, which makes tooling, dependency analysis, and maintenance worse.

The only meaningful advantage is indirection: a stable internal coordinate can point to different JDBC jars. That can make sense in a tightly controlled internal platform, but it is hard to justify in a small standard Spring Boot service.

The cleaner design is to declare the real dependency directly, usually as:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

That removes the alias install step, makes local and container builds behave the same way, and makes the project easier to understand.
