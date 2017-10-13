package nl.biopet.tools.extracttagsfromgtf

import java.io.PrintWriter

import nl.biopet.utils.ngs.annotation.Feature
import nl.biopet.utils.tool.ToolCommand

import scala.io.Source

object ExtractTagsFromGtf extends ToolCommand {
  def main(args: Array[String]): Unit = {
    val parser = new ArgsParser(toolName)
    val cmdArgs =
      parser.parse(args, Args()).getOrElse(throw new IllegalArgumentException)

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
        writer.println(cmdArgs.tags.map(f.attributes.get).map(_.getOrElse(".")).mkString("\t"))
      }

    reader.close()
    writer.close()

    logger.info("Done")
  }
}
