package com.drabatx.animegall.presentation.view.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.drabatx.animegall.presentation.navigation.AppNavigation
import com.drabatx.animegall.presentation.view.theme.AnimeGallTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimeGallTheme {
                AppNavigation()
            }
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MyApp() {
//    val topAnimViewModel: TopAnimViewModel = viewModel()
//    Scaffold(topBar = {
//        TopAppBar(
//            title= {
//                Text(
//                    text = stringResource(id = R.string.app_name),
//                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
//                    color = MaterialTheme.colorScheme.onPrimary,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.fillMaxWidth()
//                )
//            },
//            colors = TopAppBarDefaults.topAppBarColors(
//                containerColor = MaterialTheme.colorScheme.primary // Color de fondo
//            ),
//            navigationIcon = {
//                Icon(
//                    painter = painterResource(id = R.drawable.logo), // Reemplaza con tu icono
//                    contentDescription = "Logo",
//                    modifier = Modifier.offset(x = margin_small),
//                    tint = MaterialTheme.colorScheme.onPrimary // Ajusta el color del icono si es necesario
//                )
//            }
//
//        )
//    }, content = { padding ->
////        AnimeScreen(Modifier.padding(padding), topAnimViewModel)
//    })
//}

