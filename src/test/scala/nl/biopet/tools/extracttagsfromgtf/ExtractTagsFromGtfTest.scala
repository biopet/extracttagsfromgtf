package nl.biopet.tools.extracttagsfromgtf

import nl.biopet.utils.test.tools.ToolTest
import org.testng.annotations.Test

class ExtractTagsFromGtfTest extends ToolTest[Args] {
  @Test
  def testNoArgs(): Unit = {
    intercept[IllegalArgumentException] {
      ExtractTagsFromGtf.main(Array())
    }
  }

  //Simple tool, simple description
  override def minDescriptionWords: Int = 10

  def toolCommand: ExtractTagsFromGtf.type = ExtractTagsFromGtf
}
