package com.drabatx.animegall.data.network

import com.drabatx.animegall.data.model.response.anime.topanime.TopAnimeResponse
import retrofit2.http.GET

interface ApiService {

    @GET("top/anime")
    suspend fun getTopAnime(): TopAnimeResponse
}