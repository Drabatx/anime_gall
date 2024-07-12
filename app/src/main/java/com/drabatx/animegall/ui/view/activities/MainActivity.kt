package com.drabatx.animegall.ui.view.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.drabatx.animegall.data.model.response.anime.topanime.TopAnimeResponse
import com.drabatx.animegall.ui.mappers.TopAnimeResponseToAnimeItemListMapper
import com.drabatx.animegall.ui.model.AnimeItemList
import com.drabatx.animegall.ui.theme.AnimeGallTheme
import com.drabatx.animegall.ui.view.viewmodels.TopAnimViewModel
import com.drabatx.animegall.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimeGallTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
//                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val topAnimViewModel: TopAnimViewModel = viewModel()
    Scaffold(content = { padding ->
        TopAnimeScreen(Modifier.padding(padding), topAnimViewModel)
    })
}

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
        contentPadding = innerPadding
    ) {
        items(animeList.size) { index ->
            ItemAnimeView(animeList[index])
        }
    }
}

@Composable
fun ItemAnimeView(animeItem: AnimeItemList) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
        ) {
            val (imageAnimeItem, rankText, statusText, gradientLayout, nameText, yearText, scoreText) = createRefs()
            AsyncImage(
                model = animeItem.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .constrainAs(imageAnimeItem) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .fillMaxWidth()
                    .fillMaxHeight()
            )

            Text(
                text = "Rank: ${animeItem.rank}",
//                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .constrainAs(rankText) {
                        top.linkTo(imageAnimeItem.bottom, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                    }
            )

            Text(
                text = animeItem.status,
//                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .constrainAs(statusText) {
                        top.linkTo(imageAnimeItem.bottom, margin = 8.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                    }
            )

            ConstraintLayout(
                modifier = Modifier
                    .constrainAs(gradientLayout) {
                        bottom.linkTo(imageAnimeItem.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
//                    .background(
//                        brush = Brush.verticalGradient(
//                            colors = listOf(Color.Black, Color.Transparent)
//                        )
//                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = animeItem.name,
//                    style = MaterialTheme.typography.h6.copy(color = Color.White, fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .constrainAs(nameText) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(scoreText.start, margin = 8.dp)
                            width = Dimension.fillToConstraints
                        }
                )

                Text(
                    text = "Year: ${animeItem.year}",
//                    style = MaterialTheme.typography.body2.copy(color = Color.White),
                    modifier = Modifier
                        .constrainAs(yearText) {
                            top.linkTo(nameText.bottom, margin = 8.dp)
                            start.linkTo(parent.start)
                        }
                )

                Text(
                    text = "Score: ${animeItem.score}",
//                    style = MaterialTheme.typography.body2.copy(color = Color.White),
                    modifier = Modifier
                        .constrainAs(scoreText) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAnimeItemListGrid() {
    val sampleData = listOf(
        AnimeItemList(
            name = "Chobits",
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

