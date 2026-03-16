package com.example.printhelper.data

import com.example.printhelper.domain.PrintManager
import com.example.printhelper.domain.RenderedPage
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory
import java.awt.Desktop
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.print.PageFormat
import java.awt.print.Printable
import java.awt.print.PrinterJob
import java.io.File
import javax.imageio.ImageIO
import javax.print.attribute.HashPrintJobAttributeSet
import javax.print.attribute.HashPrintRequestAttributeSet
import javax.print.attribute.PrintRequestAttributeSet

actual fun createPrintManager(): PrintManager = DesktopPrintManager()

class DesktopPrintManager: PrintManager{
    override suspend fun print(pages: List<RenderedPage>) {
        /*
        // 1. Pdf with PDFBox
        val doc = PDDocument()
        pages.forEach { page ->
            val bufferedImage = ImageIO.read(page.imageBytes.inputStream())
            val pdPage = PDPage(PDRectangle(page.widthPx.toFloat(), page.heightPx.toFloat()))
            doc.addPage(pdPage)
            val contentStream = PDPageContentStream(doc, pdPage)
            val pdImage = LosslessFactory.createFromImage(doc, bufferedImage)
            val mediaBox = pdPage.mediaBox
            contentStream.drawImage(
                pdImage,
                0f,
                0f,
                mediaBox.width,
                mediaBox.height
            )
            contentStream.close()
        }

        // 2. temp
        val tempFile = File.createTempFile("print_", ".pdf")
        doc.save(tempFile)
        doc.close()

        // 3. OS dialog (native)
        Desktop.getDesktop().print(tempFile)

        tempFile.delete()
*/

        //Jav awt stuff

        val job = PrinterJob.getPrinterJob()
        job.setPrintable(PagePrinter(pages))
        val attributes: PrintRequestAttributeSet = HashPrintRequestAttributeSet()
        if(job.printDialog(attributes)){
            try {
                job.print(attributes) // Pass the same attributes to print
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

    }
}

private class PagePrinter(private val pages: List<RenderedPage>): Printable{

    //java awt one, looks mad ugly and old and non native
    override fun print(graphics: Graphics, pageFormat: PageFormat, pageIndex: Int): Int {
        if(pageIndex >= pages.size)
            return Printable.NO_SUCH_PAGE

        val image = ImageIO.read(pages[pageIndex].imageBytes.inputStream())
        (graphics as Graphics2D).drawImage(image,0,0,null)
        return Printable.PAGE_EXISTS
        //basically  I draw on JVM provided canva every page and then when print sent it prints every canva page
        //lowkey think of it as control inversion. Stuff is handled in the back I just needed to override and make my customizations.
    }
}