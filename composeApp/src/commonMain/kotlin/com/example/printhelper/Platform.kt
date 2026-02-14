package com.example.printhelper

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform