package com.example.printhelper.domain

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class RenderedPage(
    val fileId: Uuid,
    val imageBytes: ByteArray,
    val widthPx: Int,
    val heightPx: Int
)
