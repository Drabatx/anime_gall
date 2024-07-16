package com.drabatx.animegall.presentation.view.utils

enum class AnimeFilter(val id: Int, val text: String, val filter :String) {
    TOP(0, "Top", ""),
    POPULARES(1, "Populares","bypopularity"),
    PROXIMAMENTE(2, "Proximamente","upcoming"),
    FAVORITOS(3, "Favoritos","favorite"),
    AIRING(4, "En Transmisión","airing")
}