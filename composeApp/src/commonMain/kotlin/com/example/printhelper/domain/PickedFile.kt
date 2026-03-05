package com.example.printhelper.domain

import io.github.vinceglb.filekit.PlatformFile
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class PickedFile(
    val id: Uuid = Uuid.random(),
    val name: String,
    val fileType: FileType,
    val platformFile: PlatformFile //better to have bytes read on demand
)