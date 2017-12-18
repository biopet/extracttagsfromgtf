package nl.biopet.tools.extracttagsfromgtf

import java.io.File

import nl.biopet.utils.tool.AbstractOptParser

class ArgsParser(toolCommand: ToolCommand[Args])
    extends AbstractOptParser[Args](toolCommand) {
  opt[File]('i', "refflat") required () unbounded () valueName "<file>" action {
    (x, c) =>
      c.copy(outputFile = x)
  } text "Input refFlat file. Mandatory"
  opt[File]('o', "gtfFile") required () unbounded () valueName "<file>" action {
    (x, c) =>
      c.copy(gtfFile = x)
  } text "Output gtf file. Mandatory"
  opt[String]('t', "tag") required () unbounded () valueName "<string>" action {
    (x, c) =>
      c.copy(tags = c.tags ::: x :: Nil)
  } text "Tags to extract"
  opt[String]('f', "feature") unbounded () valueName "<string>" action {
    (x, c) =>
      c.copy(feature = Some(x))
  } text "Filter for only this feature type"
}
