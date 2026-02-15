package com.example.printhelper.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.printhelper.data.pickFiles
import kotlinx.coroutines.launch

@Composable
@Preview
fun MainScreen() {
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ){
        Text("Print Helper")
        Button(onClick = {
            scope.launch {
                val files = pickFiles()
                println(files)
            }
        }){
            Text("Add files")
        }

    }
}