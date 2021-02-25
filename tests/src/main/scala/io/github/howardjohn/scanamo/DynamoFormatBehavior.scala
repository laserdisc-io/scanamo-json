package io.github.howardjohn.scanamo

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.{ Assertion, EitherValues }
import org.scalatest.matchers.should.Matchers
import org.scanamo.DynamoFormat

trait DynamoFormatBehavior extends Matchers with EitherValues { this: AnyFunSuite =>
  def dynamoFormatTest[T](parse: String => Either[Any, T])(format: DynamoFormat[T]): Unit = {
    def roundTrip(input: String, expected: String): Assertion = {
      val json      = parse(input)
      val attribute = format.write(json.right.value).toAttributeValue
      val jsonResp  = format.read(attribute)
      attribute.toString should be(expected)
      assert(jsonResp === json)
    }

    test("empty map")(roundTrip("{}", "AttributeValue(M={})"))
    test("integer value")(
      roundTrip(
        """{"a":1}""",
        "AttributeValue(M={a=AttributeValue(N=1)})"
      )
    )
    test("string value")(
      roundTrip(
        """{"a":"b"}""",
        "AttributeValue(M={a=AttributeValue(S=b)})"
      )
    )
    test("bool value")(
      roundTrip(
        """{"a":true}""",
        "AttributeValue(M={a=AttributeValue(BOOL=true)})"
      )
    )
    test("null value")(
      roundTrip(
        """{"a":null}""",
        "AttributeValue(M={a=AttributeValue(NUL=true)})"
      )
    )
    test("map map")(
      roundTrip(
        """{"nested":{"a":1}}""",
        "AttributeValue(M={nested=AttributeValue(M={a=AttributeValue(N=1)})})"
      )
    )
    test("int list value")(
      roundTrip(
        """{"a":[1,2,3]}""",
        "AttributeValue(M={a=AttributeValue(L=[AttributeValue(N=1), AttributeValue(N=2), AttributeValue(N=3)])})"
      )
    )
    test("string list value")(
      roundTrip(
        """{"a":["b","c","d"]}""",
        "AttributeValue(M={a=AttributeValue(L=[AttributeValue(S=b), AttributeValue(S=c), AttributeValue(S=d)])})"
      )
    )
    test("mixed values")(
      roundTrip(
        """{"a":1,"b":"value"}""",
        "AttributeValue(M={a=AttributeValue(N=1), b=AttributeValue(S=value)})"
      )
    )
    test("mixed list") {
      roundTrip(
        """{"a":[1,"b",false,null]}""",
        "AttributeValue(M={a=AttributeValue(L=[AttributeValue(N=1), AttributeValue(S=b), AttributeValue(BOOL=false), AttributeValue(NUL=true)])})"
      )
    }
    test("nested list") {
      roundTrip(
        """{"a":[1,[2,[3]]]}""",
        "AttributeValue(M={a=AttributeValue(L=[AttributeValue(N=1), AttributeValue(L=[AttributeValue(N=2), AttributeValue(L=[AttributeValue(N=3)])])])})"
      )
    }
    test("just bool")(
      roundTrip("true", "AttributeValue(BOOL=true)")
    )
    test("just number")(roundTrip("1", "AttributeValue(N=1)"))
    test("just string")(
      roundTrip("\"string\"", "AttributeValue(S=string)")
    )
    test("just list")(
      roundTrip(
        "[1,2,3]",
        "AttributeValue(L=[AttributeValue(N=1), AttributeValue(N=2), AttributeValue(N=3)])"
      )
    )
  }
}
