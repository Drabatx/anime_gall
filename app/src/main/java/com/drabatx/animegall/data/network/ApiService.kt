package com.drabatx.animegall.data.network

import com.drabatx.animegall.data.model.response.anime.topanime.TopAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top/anime")
    suspend fun getTopAnime(@Query("page") page: Int): TopAnimeResponse
}