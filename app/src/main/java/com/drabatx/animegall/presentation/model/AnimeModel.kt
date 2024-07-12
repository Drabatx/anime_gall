package com.drabatx.animegall.presentation.model

data class AnimeModel(
    val name: String,
    val status: String,
    val score: Double,
    val rank: Int,
    val year: Int,
    val image: String
) {
    class Builder {
        private var name: String = ""
        private var status: String = ""
        private var score: Double = 0.0
        private var rank: Int = 0
        private var year: Int = 0
        private var image: String = ""

        fun setName(name: String) = apply { this.name = name }
        fun setStatus(status: String) = apply {
            when {
                status.equals("Finished Airing") -> {
                    this.status = "Finalizado"
                }

                status.equals("Currently Airing") -> {
                    this.status = "En transmisión"
                }
                status.equals("Not yet aired") -> {
                    this.status = "Próximamente"
                }
                else -> {
                    status
                }
            }
        }

        fun setScore(score: Double) = apply { this.score = score }
        fun setRank(rank: Int) = apply { this.rank = rank }
        fun setYear(year: Int) = apply { this.year = year }
        fun setImage(image: String) = apply { this.image = image }

        fun build(): AnimeModel {
            return AnimeModel(name, status, score, rank, year, image)
        }
    }
}
