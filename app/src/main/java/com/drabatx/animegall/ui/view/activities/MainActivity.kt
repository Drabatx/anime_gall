package com.drabatx.animegall.ui.view.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.drabatx.animegall.ui.theme.AnimeGallTheme
import com.drabatx.animegall.ui.view.screens.TopAnimeScreen
import com.drabatx.animegall.ui.view.viewmodels.TopAnimViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimeGallTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
//                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val topAnimViewModel: TopAnimViewModel = viewModel()
    Scaffold(content = { padding ->
        TopAnimeScreen(Modifier.padding(padding), topAnimViewModel)
    })
}

