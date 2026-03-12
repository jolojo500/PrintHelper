package com.example.printhelper.data

import com.example.printhelper.domain.PrintManager
import com.example.printhelper.domain.RenderedPage
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.print.PageFormat
import java.awt.print.Printable
import java.awt.print.PrinterJob
import javax.imageio.ImageIO

actual fun createPrintManager(): PrintManager = DesktopPrintManager()

class DesktopPrintManager: PrintManager{
    override suspend fun print(pages: List<RenderedPage>) {
        val job = PrinterJob.getPrinterJob()
        job.setPrintable(PagePrinter(pages))
        if(job.printDialog()){
            job.print()
        }
    }
}

private class PagePrinter(private val pages: List<RenderedPage>): Printable{
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