package com.drabatx.animegall.presentation.mappers

import com.drabatx.animegall.data.model.response.anime.animebyid.Data
import com.drabatx.animegall.presentation.model.AnimeDetailsModel

object AnimeDetailsResponseToAnimeDetailsModelMapper {
    fun map(anime: Data) = AnimeDetailsModel.Builder()
        .setName(anime.title)
        .setScore(anime.score)
        .setImageMedium(anime.images.webp.image_url)
        .setImageBig(anime.images.webp.large_image_url)
        .setRanked(anime.rank)
        .setPopularity(anime.popularity)
        .setYear(anime.year)
        .setSynopsis(anime.synopsis)
        .setBackground(anime.background)
        .setGenders(anime.genres.map { it.name })
        .setStudio(anime.studios.joinToString(", ") { it.name })
        .setTrailer(anime.trailer.embed_url)
        .setMembers(anime.members)
        .build()
}