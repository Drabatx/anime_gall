package com.drabatx.animegall.presentation.model

data class FullAnimeModel(
    val name: String,
    val score: Double,
    val imageMedium: String,
    val imageBig: String,
    val ranked: Int,
    val popularity: Int,
    val year: Int,
    val synopsis: String, val background: String?,
    val genders: List<String>,
    val studio: String,
    val trailer: String?
)