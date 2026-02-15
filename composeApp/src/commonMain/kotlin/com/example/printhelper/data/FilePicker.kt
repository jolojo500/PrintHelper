package com.example.printhelper.data

import com.example.printhelper.domain.PickedFile


expect suspend fun pickFiles(): List<PickedFile>
