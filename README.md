# Scanamo Json

[![Build Status](https://travis-ci.org/laserdisc-io/scanamo-json.svg?branch=master)](https://travis-ci.org/laserdisc-io/scanamo-json)
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

### Play Json

First, add the dependency:

```scala
    libraryDependencies += "io.laserdisc" %% "scanamo-play-json" % "1.0.5"
```

Finally, the format can be imported with:

```scala
    import io.github.howardjohn.scanamo.PlayJsonDynamoFormat._
```

This provides a `DynamoFormat[T]` for all `T` with a (play-json) `Format`.
