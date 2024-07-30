package com.drabatx.animegall.presentation.model

data class AnimeDetailsModel(
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
    val trailer: String?,
    val members: Int = 0,
) {
    class Builder {
        private var name: String = ""
        private var score: Double = 0.0
        private var imageMedium: String = ""
        private var imageBig: String = ""
        private var ranked: Int = 0
        private var popularity: Int = 0
        private var year: Int = 0
        private var synopsis: String = ""
        private var background: String? = null
        private var genders: List<String> = emptyList()
        private var studio: String = ""
        private var trailer: String? = null
        private var members: Int = 0
        fun setName(name: String) = apply { this.name = name }
        fun setScore(score: Double) = apply { this.score = score }
        fun setImageMedium(imageMedium: String) = apply { this.imageMedium = imageMedium }
        fun setImageBig(imageBig: String) = apply { this.imageBig = imageBig }
        fun setRanked(ranked: Int) = apply { this.ranked = ranked }
        fun setPopularity(popularity: Int) = apply { this.popularity = popularity }
        fun setYear(year: Int) = apply { this.year = year }
        fun setSynopsis(synopsis: String) = apply { this.synopsis = synopsis }
        fun setBackground(background: String?) = apply { this.background = background }
        fun setGenders(genders: List<String>) = apply { this.genders = genders }
        fun setStudio(studio: String) = apply { this.studio = studio }
        fun setTrailer(trailer: String?) = apply { this.trailer = trailer }
        fun setMembers(members: Int) = apply { this.members = members }
        fun build() = AnimeDetailsModel(
            name, score, imageMedium, imageBig, ranked, popularity, year, synopsis,
            background, genders, studio, trailer, members
        )
    }
}