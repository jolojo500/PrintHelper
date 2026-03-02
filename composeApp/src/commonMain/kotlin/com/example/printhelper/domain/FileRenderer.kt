package com.example.printhelper.domain

interface FileRenderer {
    suspend fun render(file: PickedFile): List<RenderedPage>
}