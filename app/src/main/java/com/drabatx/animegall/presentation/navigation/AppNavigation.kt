package com.drabatx.animegall.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.drabatx.animegall.presentation.view.screens.AnimeDescriptionScreen
import com.drabatx.animegall.presentation.view.screens.AnimeScreen
import com.drabatx.animegall.presentation.view.viewmodels.AnimeByIdViewModel
import com.drabatx.animegall.presentation.view.viewmodels.TopAnimViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val animeViewModel: TopAnimViewModel = viewModel()
    val animeByIdViewModel: AnimeByIdViewModel = viewModel()

    NavHost(navController = navController, startDestination = AppScreens.AnimeList.route) {
        composable(route = AppScreens.AnimeList.route) {
            AnimeScreen(navController = navController, viewModel = animeViewModel)
        }
        composable(
            route = AppScreens.AnimeFull.route,
            arguments = listOf(navArgument("animeId") {
                type = NavType.IntType
            })
        ) {
            val animeId = it.arguments?.getInt("animeId") ?: 0
            val animeName = it.arguments?.getString("animeName") ?: ""
            AnimeDescriptionScreen(
                navController = navController,
                viewModel = animeByIdViewModel,
                animeId = animeId,
                animeName = animeName
            )
        }
    }
}