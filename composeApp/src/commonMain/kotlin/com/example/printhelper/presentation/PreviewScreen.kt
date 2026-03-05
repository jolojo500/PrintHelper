package com.example.printhelper.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage

@Composable
fun PreviewScreen(viewModel: MainViewModel = viewModel { MainViewModel() }){
    val pages by viewModel.renderedPages.collectAsStateWithLifecycle()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 200.dp),
        contentPadding = PaddingValues(8.dp)
    ){
        itemsIndexed(pages){index , page ->
            Column(
                modifier = Modifier.padding(8.dp)
            ){
                AsyncImage(
                    model = page.imageBytes,
                    contentDescription = "Page ${index + 1}",
                    modifier = Modifier.fillMaxWidth().aspectRatio(
                        if(page.widthPx > 0 && page.heightPx > 0) page.widthPx.toFloat() / page.heightPx.toFloat()
                        else 0.77f //A4 format
                    )
                )
                Text("Page ${index + 1}")

            }
        }
    }
}