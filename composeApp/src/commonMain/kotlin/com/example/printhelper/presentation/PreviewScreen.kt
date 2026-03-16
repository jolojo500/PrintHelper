package com.example.printhelper.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyGridState

@Composable
fun PreviewScreen(viewModel: MainViewModel = viewModel { MainViewModel() }){
    val pages by viewModel.renderedPages.collectAsStateWithLifecycle()

    val gridState = rememberLazyGridState()
    val reorderableState = rememberReorderableLazyGridState(gridState) { from, to ->
        viewModel.reorderPages(from.index, to.index)
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 200.dp),
        state = gridState,
        contentPadding = PaddingValues(8.dp)
    ){  //dont got a page id so we make hash on the fly
        itemsIndexed(pages, key = { _, page -> page.hashCode() }){index , page ->
            ReorderableItem(reorderableState, key = page.hashCode()){ isDragging ->
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .draggableHandle()
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
        item { Button(onClick = { viewModel.printPages() }) {
            Text("Print")
        } }
    }
}