package com.example.printhelper.data

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import com.example.printhelper.domain.FileRenderer
import com.example.printhelper.domain.PickedFile
import com.example.printhelper.domain.RenderedPage
import io.github.vinceglb.filekit.readBytes
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.uuid.ExperimentalUuidApi

actual fun createPdfRenderer(): FileRenderer = AndroidPdfRenderer()

class AndroidPdfRenderer: FileRenderer{
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun render(file: PickedFile): List<RenderedPage> {
        val bytes = file.platformFile.readBytes()

        //need physical file and filekit doesnt seem to provide any ref to it so
        val tempFile = File.createTempFile("pdf_render",".pdf")
        tempFile.writeBytes(bytes)

        val parcel = ParcelFileDescriptor.open(tempFile, ParcelFileDescriptor.MODE_READ_ONLY)
        val pdfRenderer = PdfRenderer(parcel)

        val pages = (0 until pdfRenderer.pageCount).map { pageIndex ->
            val page = pdfRenderer.openPage(pageIndex)

            //bitmap of 300 DPI — A4 = 2480x3508 scaled according to page
            val scale = 300f / 72f //pdf standard is 72dpi
            val width = (page.width * scale).toInt()
            val height = (page.height * scale).toInt()

            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_PRINT)
            page.close()

            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

            RenderedPage(
                file.id,
                imageBytes = outputStream.toByteArray(),
                widthPx = width,
                heightPx = height
            )
        }

        pdfRenderer.close()
        parcel.close()
        tempFile.delete() //cleanup
        return pages

    }

}