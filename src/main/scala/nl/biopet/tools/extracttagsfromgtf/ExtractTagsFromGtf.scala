package nl.biopet.tools.extracttagsfromgtf

import java.io.PrintWriter

import nl.biopet.utils.ngs.annotation.Feature
import nl.biopet.utils.tool.ToolCommand

import scala.io.Source

object ExtractTagsFromGtf extends ToolCommand[Args] {

  def emptyArgs: Args = Args()
  def argsParser = new ArgsParser(this)

  def main(args: Array[String]): Unit = {
    val cmdArgs = cmdArrayToArgs(args)

    logger.info("Start")

    val reader = Source.fromFile(cmdArgs.gtfFile)
    val writer = new PrintWriter(cmdArgs.outputFile)
    writer.println(cmdArgs.tags.mkString("#", "\t", ""))

    reader
      .getLines()
      .filter(!_.startsWith("#"))
      .map(Feature.fromLine)
      .filter(f => cmdArgs.feature.forall(_ == f.feature))
      .foreach { f =>
        writer.println(
          cmdArgs.tags
            .map(f.attributes.get)
            .map(_.getOrElse("."))
            .mkString("\t"))
      }

    reader.close()
    writer.close()

    logger.info("Done")
  }

  def descriptionText: String =
    s"""
      |
      |
      |$toolName extracts tags from a GTF refflat file
      |and outputs the results in tsv format.
    """.stripMargin

  def manualText: String =
    s"""
       |$toolName can select tags from a refflat file and
       |optionally filter for a specific feature.
       |The number of tags that can be selected is unlimited,
       |at least one tag is required.
     """.stripMargin

  def exampleText: String =
    s"""
       |To extract `tag1`, `tag2` and `tag3` from `inputRefflat`
       |and filter only for feature type `feature`:
       |${example("-i",
                  "inputRefflat",
                  "-o",
                  "output.tsv",
                  "-t",
                  "tag1",
                  "-t",
                  "tag2",
                  "-t",
                  "tag3",
                  "-f",
                  "feature")}
       |
     """.stripMargin
}
