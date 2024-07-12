package com.drabatx.animegall.data.model.response.anime.topanime

data class TopAnimeResponse(
    val data: List<Anime>,
    val pagination: Pagination
)