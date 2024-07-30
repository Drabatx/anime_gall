package com.drabatx.animegall.data.network

import com.drabatx.animegall.data.model.response.anime.AnimeDetailsResponse
import com.drabatx.animegall.data.model.response.anime.TopAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int,
        @Query("filter") filter: String,
        @Query("sfw") sfw: Boolean = true
    ): TopAnimeResponse

    @GET("anime/{animeId}/full")
    suspend fun findAnimeById(@Path("animeId") animId: Int):AnimeDetailsResponse
}