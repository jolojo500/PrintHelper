package com.example.printhelper.domain

interface PrintManager {
    suspend fun print(pages: List<RenderedPage>)
}