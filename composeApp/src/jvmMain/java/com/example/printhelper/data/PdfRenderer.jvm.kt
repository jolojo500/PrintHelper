package com.example.printhelper.data

import com.example.printhelper.domain.FileRenderer
import com.example.printhelper.domain.PickedFile
import com.example.printhelper.domain.RenderedPage
import io.github.vinceglb.filekit.readBytes
import org.apache.pdfbox.Loader
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.PDFRenderer
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import kotlin.uuid.ExperimentalUuidApi

actual fun createPdfRenderer(): FileRenderer = DesktopPdfRenderer()


class DesktopPdfRenderer: FileRenderer{
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun render(file: PickedFile): List<RenderedPage> {
        val bytes = file.platformFile.readBytes()

        val document = Loader.loadPDF(bytes)
        val pdfRenderer = PDFRenderer(document) //turns into java bufferedimage


        val pages = (0 until document.numberOfPages).map { pageIndex ->
            val bufferedImage = pdfRenderer.renderImageWithDPI(pageIndex, 300f)

            //bufferedimage to byte array
            val outputStream = ByteArrayOutputStream()
            ImageIO.write(bufferedImage, "PNG", outputStream)

            RenderedPage(
                file.id,
                imageBytes = outputStream.toByteArray(),
                widthPx = bufferedImage.width,
                heightPx = bufferedImage.height
            )
        }

        document.close()
        return pages
        
    }
}