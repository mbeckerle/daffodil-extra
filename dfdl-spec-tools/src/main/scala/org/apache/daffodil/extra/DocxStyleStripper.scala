package org.apache.daffodil.extra

import scala.jdk.CollectionConverters._

import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart
import org.docx4j.wml.Style

object DocxStyleStripper {

  def main(args: Array[String]): Unit = {
    val inputFilePath = "doc/gwdrp-dfdl-v1.0.8-GFD-R-P.240.docx" // Input docx file path
    val outputFilePath =
      "doc/gwdrp-dfdl-v1.0.8-GFD-R-P.240.stripped.docx" // Output docx file path

    // Load the .docx file
    val wordMLPackage = WordprocessingMLPackage.load(new java.io.File(inputFilePath))
    val mainDocumentPart = wordMLPackage.getMainDocumentPart

    // Traverse and remove styles
    removeAllStyles(mainDocumentPart)

    // Save the result
    wordMLPackage.save(new java.io.File(outputFilePath))
  }

  def removeAllStyles(mainDocumentPart: MainDocumentPart): Unit = {
    // Get all the contents from the document
    val contents = mainDocumentPart.getContent.asScala

    // Process each content object
    contents.foreach {
      case paragraph: org.docx4j.wml.P =>
        // Remove paragraph styles
        paragraph.setPPr(null)
      case run: org.docx4j.wml.R =>
        // Remove run styles
        run.setRPr(null)
      case _ => // Do nothing for other types
    }

    // Optionally, remove all styles defined in the style part
    val stylesPart = mainDocumentPart.getStyleDefinitionsPart
    if (stylesPart != null) {
      stylesPart.setJaxbElement(new org.docx4j.wml.Styles())
    }
  }
}
