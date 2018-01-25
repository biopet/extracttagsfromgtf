/*
 * Copyright (c) 2017 Sequencing Analysis Support Core - Leiden University Medical Center
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
    writer.print("#")
    if (cmdArgs.addPositions) writer.print("contig\tstart\tstop\t")
    writer.println(cmdArgs.tags.mkString("\t"))

    reader
      .getLines()
      .filter(!_.startsWith("#"))
      .map(Feature.fromLine)
      .filter(f => cmdArgs.feature.forall(_ == f.feature))
      .foreach { f =>
        if (cmdArgs.addPositions)
          writer.print(s"${f.contig}\t${f.start}\t${f.end}\t")
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
       |$toolName extracts tags from a GTF refflat file
       |and outputs the results in tsv format.
       |
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
