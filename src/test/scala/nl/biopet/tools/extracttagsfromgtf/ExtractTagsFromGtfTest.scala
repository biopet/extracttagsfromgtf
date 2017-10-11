package nl.biopet.tools.extracttagsfromgtf

import nl.biopet.test.BiopetTest
import org.testng.annotations.Test

class ExtractTagsFromGtfTest extends BiopetTest {
  @Test
  def testNoArgs(): Unit = {
    intercept[IllegalArgumentException] {
      ExtractTagsFromGtf.main(Array())
    }
  }
}
