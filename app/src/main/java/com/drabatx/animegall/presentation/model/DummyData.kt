package com.drabatx.animegall.presentation.model

import com.drabatx.animegall.presentation.view.utils.AnimeFilter

val sampleData = listOf(
    AnimeModel(
        name = "Full Metal Alchemist: Brotherhood",
        status = "Completed",
        score = 8.0,
        rank = 123,
        year = 2002,
        image = "https://cdn.myanimelist.net/images/anime/1015/138006.jpg",
        filter = AnimeFilter.TOP,
        id = 1
    ),
    AnimeModel(
        name = "Diebuster",
        status = "Completed",
        score = 7.5,
        rank = 234,
        year = 2004,
        image = "https://cdn.myanimelist.net/images/anime/1015/138006.jpg",
        filter = AnimeFilter.TOP,
        id = 2
    )
)

val SampleFullAnimeModel = AnimeDetailsModel(
    name = "Sousou no Frieren",
    score = 9.35,
    imageMedium = "https://cdn.myanimelist.net/images/anime/1015/138006.webp",
    imageBig = "https://cdn.myanimelist.net/images/anime/1015/138006l.webp",
    ranked = 1,
    popularity = 237,
    year = 2023,
    synopsis = "During their decade-long quest to defeat the Demon King, the members of the hero's party—Himmel himself, the priest Heiter, the dwarf warrior Eisen, and the elven mage Frieren—forge bonds through adventures and battles, creating unforgettable precious memories for most of them.\\n\\nHowever, the time that Frieren spends with her comrades is equivalent to merely a fraction of her life, which has lasted over a thousand years. When the party disbands after their victory, Frieren casually returns to her \\\"usual\\\" routine of collecting spells across the continent. Due to her different sense of time, she seemingly holds no strong feelings toward the experiences she went through.\\n\\nAs the years pass, Frieren gradually realizes how her days in the hero's party truly impacted her. Witnessing the deaths of two of her former companions, Frieren begins to regret having taken their presence for granted; she vows to better understand humans and create real personal connections. Although the story of that once memorable journey has long ended, a new tale is about to begin.\\n\\n[Written by MAL Rewrite]", // (texto abreviado)
    background = "Sousou no Frieren fue lanzado en Blu-ray...", // (texto abreviado)
    genders = listOf("Adventure", "Drama", "Fantasy"),
    studio = "Madhouse",
    trailer = "https://www.youtube.com/watch?v=ZEkwCGJ3o7M",
    members = 123456

)