package nl.biopet.tools.extracttagsfromgtf

import nl.biopet.test.BiopetTest
import org.testng.annotations.Test

object ExtractTagsFromGtfTest extends BiopetTest {
  @Test
  def testNoArgs(): Unit = {
    intercept[IllegalArgumentException] {
      ToolTemplate.main(Array())
    }
  }
}
