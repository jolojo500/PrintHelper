package com.example.printhelper.domain

data class RenderedPage(
    val imageBytes: ByteArray,
    val widthPx: Int,
    val heightPx: Int
)
