# Scanamo Json
[![Build](https://github.com/laserdisc-io/scanamo-json/actions/workflows/build.yml/badge.svg)](https://github.com/laserdisc-io/scanamo-json/actions/workflows/build.yml)
[![Release](https://github.com/laserdisc-io/scanamo-json/actions/workflows/release.yml/badge.svg)](https://github.com/laserdisc-io/scanamo-json/actions/workflows/release.yml)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.laserdisc/scanamo-circe_2.12/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.laserdisc/scanamo-json_2.12)

Scanamo Json provides `DynamoFormat`s for popular Scala Json libraries. The format will serialize directly to DynamoDB `AttributeValue`s, allowing full use of DynamoDB while allowing arbitrary Json objects to be stored or reusing existing formats.

# Getting started

### Circe

First, add the dependency:

```scala
    libraryDependencies += "io.laserdisc" %% "scanamo-circe" % "1.0.5"
```

Finally, the format can be imported with:

```scala
    import io.github.howardjohn.scanamo.CirceDynamoFormat._
```

This provides a `DynamoFormat[T]` for all `T` with both an `Encoder` and `Decoder`.

```
YourKit supports open source projects with innovative and intelligent tools
for monitoring and profiling Java and .NET applications.
YourKit is the creator of <a href="https://www.yourkit.com/java/profiler/">YourKit Java Profiler</a>,
<a href="https://www.yourkit.com/.net/profiler/">YourKit .NET Profiler</a>,
and <a href="https://www.yourkit.com/youmonitor/">YourKit YouMonitor</a>.
```
