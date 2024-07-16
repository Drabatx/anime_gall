package com.drabatx.animegall.presentation.navigation

import androidx.annotation.StringRes
import com.drabatx.animegall.R

sealed class AppScreens(val route: String, @StringRes val resourceId: Int) {
    data object AnimeList: AppScreens("anime_list", R.string.menu_anime_list)
    data object AnimeFull: AppScreens("anime_full", R.string.menu_anime_full)
}