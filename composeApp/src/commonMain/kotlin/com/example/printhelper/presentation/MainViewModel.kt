package com.example.printhelper.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.printhelper.data.FileMapper
import com.example.printhelper.data.ImageRenderer
import com.example.printhelper.data.createPdfRenderer
import com.example.printhelper.domain.FileType
import com.example.printhelper.domain.PickedFile
import com.example.printhelper.domain.RenderedPage
import io.github.vinceglb.filekit.PlatformFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi

class MainViewModel: ViewModel() {
    private val _files = MutableStateFlow<List<PickedFile>>(emptyList())
    private val _renderedPages = MutableStateFlow<List<RenderedPage>>(emptyList())

    private val imageRenderer = ImageRenderer()
    private val pdfRenderer = createPdfRenderer()

    val files = _files.asStateFlow()
    val renderedPages = _renderedPages.asStateFlow()

    fun onFilesPicked(platformFiles: List<PlatformFile>){ //adds files to our og list
        viewModelScope.launch {
            val mapped = platformFiles.map { FileMapper.map(it) }
            _files.update { it+mapped }

            val rendered = mapped.flatMap { pickedFile ->
                when (pickedFile.fileType) {
                    FileType.IMAGE -> imageRenderer.render(pickedFile)
                    FileType.PDF -> pdfRenderer.render(pickedFile)
                    FileType.UNSUPPORTED -> emptyList()
                }
            }
            _renderedPages.update { it + rendered }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun removeFile(file: PickedFile){ //rm a file
        _files.update { it.filter { f -> f.id != file.id } }
        _renderedPages.update { it.filter { page -> page.fileId != file.id } }
    }

    //we keep all in this viewmodel snce  states arent fully independant, rendered pages depend on the files being present
    fun reorderPages(fromIndex: Int, toIndex: Int) {
        _renderedPages.update { pages ->
            pages.toMutableList().apply {
                add(toIndex, removeAt(fromIndex))
            }
        }
    }
}