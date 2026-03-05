package com.example.printhelper.data

import com.example.printhelper.domain.FileType
import com.example.printhelper.domain.PickedFile
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.extension
import io.github.vinceglb.filekit.mimeType
import io.github.vinceglb.filekit.name
import kotlin.uuid.ExperimentalUuidApi

object FileMapper {
    @OptIn(ExperimentalUuidApi::class)
    fun map(platformFile: PlatformFile): PickedFile{
        val fileType = resolveType(platformFile)
        return PickedFile(name = platformFile.name, fileType = fileType, platformFile = platformFile)
    }

    private fun resolveType(file: PlatformFile): FileType{
        val mime = file.mimeType()?.toString() ?: ""
        //print("hello in filemapper resolvetype mime before when is: "+ mime)

        val ext = file.extension.lowercase()
        return when{
            mime.startsWith("image/") || ext in listOf("png","jpg","jpeg","webp") -> FileType.IMAGE
            mime == "application/pdf" || ext == "pdf" -> FileType.PDF
            else-> FileType.UNSUPPORTED
        }
    }
}