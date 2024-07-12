package com.drabatx.animegall.ui.mappers

import com.drabatx.animegall.data.model.response.anime.topanime.Anime
import com.drabatx.animegall.ui.model.AnimeItemList

object TopAnimeResponseToAnimeItemListMapper {
    fun map(anime: Anime) = AnimeItemList.Builder()
        .setName(anime.title)
        .setStatus(anime.status)
        .setScore(anime.score.toDouble())
        .setRank(anime.rank)
        .setYear(anime.year)
        .setImage(anime.images.jpg.image_url)
        .build()
}