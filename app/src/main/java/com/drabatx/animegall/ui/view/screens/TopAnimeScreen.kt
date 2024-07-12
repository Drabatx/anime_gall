package com.drabatx.animegall.ui.view.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.drabatx.animegall.data.model.response.anime.topanime.TopAnimeResponse
import com.drabatx.animegall.ui.mappers.TopAnimeResponseToAnimeItemListMapper
import com.drabatx.animegall.ui.model.AnimeItemList
import com.drabatx.animegall.ui.theme.margin_small
import com.drabatx.animegall.ui.theme.margin_xsmall
import com.drabatx.animegall.ui.view.viewmodels.TopAnimViewModel
import com.drabatx.animegall.ui.widgets.AutoResizeText
import com.drabatx.animegall.ui.widgets.FontSizeRange
import com.drabatx.animegall.ui.widgets.RatingBar
import com.drabatx.animegall.utils.Result

@Composable
fun TopAnimeScreen(modifier: Modifier, viewModel: TopAnimViewModel) {
    val topAnimeState by viewModel.topAnimeStateFlow.collectAsState(initial = Result.Loading)
    Scaffold { innerPadding ->
        when (topAnimeState) {
            is Result.Initial -> {
                Log.d("DEBUG", "Initial")
            }

            is Result.Loading -> {
                Log.d("DEBUG", "Loading")
            }

            is Result.Error -> {
                val errorMessage = (topAnimeState as Result.Error).exception.message
                Log.d("DEBUG", "Error: $errorMessage")
            }

            is Result.Success -> {
                Log.d("DEBUG", "Success")
                val response = (topAnimeState as Result.Success<TopAnimeResponse>).data
                val topAnime = response.data.map {
                    TopAnimeResponseToAnimeItemListMapper.map(it)
                }
                viewModel.saveAnime(topAnime)
                TopAnimeList(innerPadding = innerPadding, animeList = topAnime)
            }
        }
    }

    if (viewModel.animes.isEmpty()) {
        LaunchedEffect(Unit) {
            viewModel.getTopAnimeList()
        }
    }
}

@Composable
fun TopAnimeList(innerPadding: PaddingValues, animeList: List<AnimeItemList>) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = innerPadding,
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(animeList.size) { index ->
            ItemAnimeView(animeList[index])
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemAnimeView(animeItem: AnimeItemList) {
    Card(
        shape = RoundedCornerShape(margin_small),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()

    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (imageAnimeItem, rankText, statusText, gradientLayout) = createRefs()
            AsyncImage(
                model = animeItem.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .aspectRatio(2f / 4f)
                    .constrainAs(imageAnimeItem) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )

            Box(
                modifier = Modifier
                    .constrainAs(rankText) {
                        top.linkTo(imageAnimeItem.top, margin = margin_small)
                        end.linkTo(parent.end, margin = margin_small)
                    }
                    .drawBehind {
                        val circleColor = Color(0xFFE5BE01) // Amarillo
                        val textSize = size.width / 2 // Ajusta el radio según el tamaño del texto
                        drawCircle(
                            color = circleColor,
                            radius = textSize,
                            center = Offset(size.width / 2, size.height / 2)
                        )
                    }
            ) {
                Text(
                    text = animeItem.rank.toString(), color = Color.White,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(margin_small) // Añade un poco de padding interno
                )
            }
            Box(
                modifier = Modifier
                    .constrainAs(statusText) {
                        top.linkTo(imageAnimeItem.top, margin = margin_small)
                        start.linkTo(parent.start, margin = margin_small)
                    }
                    .clip(RoundedCornerShape(8.dp)) // Esquinas redondeadas
                    .background(Color(0xFFE5BE01)) // Color de fondo amarillo
            ) {
                Text(
                    text = animeItem.status,
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(margin_xsmall),
                )
            }

            ConstraintLayout(
                modifier = Modifier
                    .constrainAs(gradientLayout) {
                        bottom.linkTo(imageAnimeItem.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0x55000000), Color(0xaa000000))
                        )
                    )
                    .padding(16.dp)
            ) {
                val (scoreText, nameText, yearText) = createRefs()
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(scoreText) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }) {
                    Text(
                        text = "${animeItem.score}",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(end = margin_small)
                    )
                    RatingBar(
                        starsModifier = Modifier.size(18.dp),
                        rating = animeItem.score / 2
                    )
                }
//
//                AutoResizeText(
//                    text = animeItem.name,
//                    fontSizeRange = FontSizeRange(min = 10.sp, max = 16.sp)
//                )

                AutoResizeText(
                    text = animeItem.name,
                    maxLines = 2,
                    fontSizeRange = FontSizeRange(min = 4.sp, max = 24.sp),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .constrainAs(nameText) {
                            top.linkTo(scoreText.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                )

                if (animeItem.year > 1900){
                    Text(
                        text = animeItem.year.toString(),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .constrainAs(yearText) {
                                top.linkTo(nameText.bottom, margin = margin_xsmall)
                                start.linkTo(parent.start)
                            }
                    )
                }


            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAnimeItemListGrid() {
    val sampleData = listOf(
        AnimeItemList(
            name = "Full Metal Alchemist: Brotherhood",
            status = "Completed",
            score = 8.0,
            rank = 123,
            year = 2002,
            image = "https://cdn.myanimelist.net/images/anime/1015/138006.jpg"
        ),
        AnimeItemList(
            name = "Diebuster",
            status = "Completed",
            score = 7.5,
            rank = 234,
            year = 2004,
            image = "https://cdn.myanimelist.net/images/anime/1015/138006.jpg"
        )
    )
    TopAnimeList(innerPadding = PaddingValues(all = 0.dp), animeList = sampleData)
}
//@Composable
//fun ItemAnimeView(animeItem: AnimeItemList) {
//    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.9f)) {
//        AsyncImage(
//            model = animeItem.image,
//            contentDescription = "Anime Image",
//            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight()
//                .aspectRatio(1f), // Adjust aspect ratio as needed
//            contentScale = ContentScale.Crop
//        )
//        Column (
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .align(Alignment.TopEnd)
//                    .padding(16.dp)
//                ) {
//            Text(text = "rank") // Replace with actual rank
//        }
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .align(Alignment.BottomStart)
//                .background(
//                    brush = Brush.verticalGradient(
//                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.2f))
//                    )
//                )
//                .padding(16.dp)
//        ) {
//            Text(
//                text = animeItem.status, // Replace with actual status
//                fontWeight = FontWeight.Bold,
//                color = Color.White
//            )
//            Text(
//                text = animeItem.name, // Replace with actual title
//                fontWeight = FontWeight.Bold,
//                color = Color.White
//            )
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(text = animeItem.year.toString(), color = Color.White) // Replace with actual year
//                Text(text = animeItem.score.toString(), color = Color.White) // Replace with actual score
//            }
//        }
//        Text(
//            text = animeItem.status, // Replace with actual status
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .align(Alignment.TopStart)
//                .padding(start = 16.dp, top = 8.dp)
//        )
//    }
//}

