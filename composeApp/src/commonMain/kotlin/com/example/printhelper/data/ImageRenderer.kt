package com.example.printhelper.data

import com.example.printhelper.domain.FileRenderer
import com.example.printhelper.domain.PickedFile
import com.example.printhelper.domain.RenderedPage
import io.github.vinceglb.filekit.readBytes
import kotlin.uuid.ExperimentalUuidApi

class ImageRenderer: FileRenderer {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun render(file: PickedFile): List<RenderedPage> {
        val bytes = file.platformFile.readBytes()
        return listOf(RenderedPage(file.id,bytes, 0,0))
    }
}