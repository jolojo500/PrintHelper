package com.example.printhelper

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import com.example.printhelper.presentation.MainScreen
import com.example.printhelper.presentation.MainViewModel
import com.example.printhelper.presentation.PreviewScreen
import io.github.vinceglb.filekit.coil.addPlatformFileSupport
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.jetbrains.compose.resources.painterResource

import printhelper.composeapp.generated.resources.Res
import printhelper.composeapp.generated.resources.compose_multiplatform

@Serializable sealed interface Route : NavKey
@Serializable data object MainRoute : Route
@Serializable data object PreviewRoute : Route

private val navConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(MainRoute::class, MainRoute.serializer())
            subclass(PreviewRoute::class, PreviewRoute.serializer())
        }
    }
}

@Composable
@Preview
fun App() {
    val backStack = rememberNavBackStack(navConfig,MainRoute)
    val viewModel = viewModel { MainViewModel() }

    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components {
                addPlatformFileSupport()
            }
            .build()
    }
    MaterialTheme {
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<MainRoute> {
                    MainScreen(
                        viewModel = viewModel,
                        onNext = { backStack.add(PreviewRoute) }
                    )
                }
                entry<PreviewRoute> {
                    PreviewScreen(viewModel = viewModel)
                }
            }
        )
    }
}