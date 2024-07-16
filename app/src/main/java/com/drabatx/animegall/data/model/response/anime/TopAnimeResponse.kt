package com.drabatx.animegall.data.model.response.anime

import com.drabatx.animegall.data.model.response.anime.topanime.Anime
import com.drabatx.animegall.data.model.response.anime.topanime.Pagination

data class TopAnimeResponse(
    val data: List<Anime>,
    val pagination: Pagination
)