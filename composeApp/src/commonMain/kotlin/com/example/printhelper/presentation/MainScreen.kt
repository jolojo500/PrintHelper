package com.example.printhelper.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import io.github.vinceglb.filekit.dialogs.FileKitMode
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.name

@Composable
@Preview
fun MainScreen() {
    val picker = rememberFilePickerLauncher(
        mode = FileKitMode.Multiple(),
        type = FileKitType.File()       //no dir
    ) { files ->
        files?.forEach {
            println("Picked: ${it.name}")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ) {
        Text("Print Helper")
        Button(onClick = { picker.launch() }) {
            Text("Add files")
        }
    }
}