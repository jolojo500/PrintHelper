package com.example.printhelper.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonShapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage


import io.github.vinceglb.filekit.dialogs.FileKitMode
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.name
import org.jetbrains.compose.resources.painterResource

@Composable
@Preview
fun MainScreen( viewModel: MainViewModel = viewModel { MainViewModel() },
                onNext: () -> Unit = {} ) { //doing viewmodel() didnt work cause jvm doesnt know it lol (worked on adnroid tho)
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
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp)
        ){
            items(files){ file ->
                Box(){ //perhaps change with card
                    //img and icon
                    AsyncImage(
                        model = file.platformFile,  //handled by filekit coil integration
                        contentDescription = file.name,
                        modifier = Modifier.aspectRatio(0.77f).fillMaxWidth(),
                        contentScale = ContentScale.Fit
                    )
                    IconButton(
                        onClick = {
                            viewModel.removeFile(file)
                        },
                        modifier = Modifier.align(Alignment.TopEnd)

                    ){
                        //Icon(imageVector = )
                        Text("x") //tmp
                    }

                    Text(
                        text = ".${file.name} (${file.fileType})",
                        modifier = Modifier.align(Alignment.BottomStart).padding(4.dp)
                    )
                }
            }
            item {
            if (files.isNotEmpty()) {
                Button(onClick = onNext) { Text("Preview") }
            }
            }
        }
        //here will add other iconbutton with + to add more files

    }
}