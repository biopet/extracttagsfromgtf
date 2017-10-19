package nl.biopet.tools.extracttagsfromgtf

import java.io.File

case class Args(outputFile: File = null,
                gtfFile: File = null,
                tags: List[String] = Nil,
                feature: Option[String] = None)
