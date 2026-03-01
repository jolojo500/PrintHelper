package com.example.printhelper.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel


import io.github.vinceglb.filekit.dialogs.FileKitMode
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.name

@Composable
@Preview
fun MainScreen(viewModel: MainViewModel = viewModel { MainViewModel() } ) { //doing viewmodel() didnt work cause jvm doesnt know it lol (worked on adnroid tho)
    val files by viewModel.files.collectAsStateWithLifecycle()
    //is the delegated quivalent to (if we didnt import compose.runtime.getValue)
    // val files = viewModel.files.collectAsStateWithLifecycle().value

    val picker = rememberFilePickerLauncher(
        mode = FileKitMode.Multiple(),
        type = FileKitType.File()       //no dir
    ) { picked ->
        picked?.let { viewModel.onFilesPicked(it) }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ) {
        Text("Print Helper")
        Button(onClick = { picker.launch() }) {
            Text("Add files")
        }
        files.forEach { file ->
            Text(".${file.name} (${file.fileType})")
        }
    }
}