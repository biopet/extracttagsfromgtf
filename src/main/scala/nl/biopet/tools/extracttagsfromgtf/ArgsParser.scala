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

import java.io.File

import nl.biopet.utils.tool.{AbstractOptParser, ToolCommand}

class ArgsParser(toolCommand: ToolCommand[Args])
    extends AbstractOptParser[Args](toolCommand) {
  opt[File]('i', "gtfFile") required () valueName "<file>" action { (x, c) =>
    c.copy(gtfFile = x)
  } text "Input refFlat file. Mandatory"
  opt[File]('o', "output") required () valueName "<file>" action { (x, c) =>
    c.copy(outputFile = x)
  } text "Output gtf file. Mandatory"
  opt[String]('t', "tag") required () unbounded () valueName "<string>" action {
    (x, c) =>
      c.copy(tags = c.tags ::: x :: Nil)
  } text "Tags to extract"
  opt[String]('f', "feature") valueName "<string>" action { (x, c) =>
    c.copy(feature = Some(x))
  } text "Filter for only this feature type"
  opt[Unit]("addPositions") action { (x, c) =>
    c.copy(addPositions = true)
  } text "Add positions to output"
}
