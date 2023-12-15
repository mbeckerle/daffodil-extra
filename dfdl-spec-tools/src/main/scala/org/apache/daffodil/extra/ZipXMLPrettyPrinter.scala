package org.apache.daffodil.extra

import java.io.{ FileInputStream, FileOutputStream }
import java.util.zip.{ ZipEntry, ZipInputStream, ZipOutputStream }
import scala.xml.{ PrettyPrinter, XML }

import org.apache.commons.io.IOUtils

object ZipXMLPrettyPrinter {

  def main(args: Array[String]): Unit = {
    val sourceZip = "doc/gwdrp-dfdl-v1.0.8-GFD-R-P.240.stripped.docx" // Source zip file
    val destinationZip = "doc/gwdrp-dfdl-v1.0.8-GFD-R-P.240.stripped.pretty.docx" // Destination zip file

    val zipIn = new ZipInputStream(new FileInputStream(sourceZip))
    val zipOut = new ZipOutputStream(new FileOutputStream(destinationZip))

    LazyList.continually(zipIn.getNextEntry).takeWhile(_ != null).foreach { originalEntry =>
      // Use Apache Commons IO to read the entire content of the entry
      val entryBytes = IOUtils.toByteArray(zipIn)

      // This copying constructor right here is why we're using the java.util.zip library
      // and not apache commons compress.
      val newEntry = new ZipEntry(originalEntry) // Copy all fields from the original entry

      if (originalEntry.getName.endsWith(".xml")) {
        // Pretty print XML
        val xml = XML.loadString(new String(entryBytes, "UTF-8"))
        val prettyPrinter = new PrettyPrinter(80, 2)
        val prettyXml = prettyPrinter.format(xml)
        zipOut.putNextEntry(newEntry)
        zipOut.write(prettyXml.getBytes("UTF-8"))
      } else {
        // Copy other files as is
        zipOut.putNextEntry(newEntry)
        zipOut.write(entryBytes)
      }

      zipOut.closeEntry()
    }

    zipIn.close()
    zipOut.close()
  }
}
