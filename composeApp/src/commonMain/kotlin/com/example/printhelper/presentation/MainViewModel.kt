package com.example.printhelper.presentation

import androidx.lifecycle.ViewModel
import com.example.printhelper.data.FileMapper
import com.example.printhelper.domain.PickedFile
import io.github.vinceglb.filekit.PlatformFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel: ViewModel() {
    private val _files = MutableStateFlow<List<PickedFile>>(emptyList())

    val files = _files.asStateFlow()

    fun onFilesPicked(platformFiles: List<PlatformFile>){ //adds files to our og list
        val mapped = platformFiles.map { FileMapper.map(it) }
        _files.update { it+mapped }
    }

    fun removeFile(file: PickedFile){ //rm a file
        _files.update { it - file }
    }
}