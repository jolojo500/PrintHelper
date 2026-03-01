package com.example.printhelper.domain

import io.github.vinceglb.filekit.PlatformFile

data class PickedFile(
    val name: String,
    val fileType: FileType,
    val platformFile: PlatformFile //better to have bytes read on demand
)