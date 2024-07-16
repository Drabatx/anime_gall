package com.drabatx.animegall.presentation.model

import com.drabatx.animegall.presentation.view.utils.AnimeFilter

data class AnimeModel(
    val name: String,
    val status: String,
    val score: Double,
    val rank: Int,
    val year: Int,
    val image: String,
    val filter: AnimeFilter
) {
    class Builder {
        private var name: String = ""
        private var status: String = ""
        private var score: Double = 0.0
        private var rank: Int = 0
        private var year: Int = 0
        private var image: String = ""
        private var filter: AnimeFilter = AnimeFilter.TOP

        fun setName(name: String) = apply { this.name = name }
        fun setStatus(status: String) = apply {
            when (status) {
                "Finished Airing" -> {
                    this.status = "Finalizado"
                }

                "Currently Airing" -> {
                    this.status = "En transmisión"
                }

                "Not yet aired" -> {
                    this.status = "Próximamente"
                }

                else -> {
                    this.status = status
                }
            }
        }

        fun setScore(score: Double) = apply { this.score = score }
        fun setRank(rank: Int) = apply { this.rank = rank }
        fun setYear(year: Int) = apply { this.year = year }
        fun setImage(image: String) = apply { this.image = image }
        fun setFilter(filter: String) = apply { this.filter = AnimeFilter.entries.find {  it.filter == filter}?:AnimeFilter.TOP }

        fun build(): AnimeModel {
            return AnimeModel(name, status, score, rank, year, image, filter)
        }
    }
}
