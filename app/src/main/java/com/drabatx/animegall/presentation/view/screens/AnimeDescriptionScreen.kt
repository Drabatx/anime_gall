package com.drabatx.animegall.presentation.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.drabatx.animegall.R
import com.drabatx.animegall.presentation.model.AnimeDetailsModel
import com.drabatx.animegall.presentation.model.SampleFullAnimeModel
import com.drabatx.animegall.presentation.view.dialogs.LoadingDialog
import com.drabatx.animegall.presentation.view.dialogs.MessageDialog
import com.drabatx.animegall.presentation.view.theme.margin_medium
import com.drabatx.animegall.presentation.view.theme.margin_xsmall
import com.drabatx.animegall.presentation.view.viewmodels.AnimeByIdViewModel
import com.drabatx.animegall.presentation.view.widgets.MainTopBar
import com.drabatx.animegall.presentation.view.widgets.shimmerBrush
import com.drabatx.animegall.utils.Result
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun AnimeDescriptionScreen(
    animeId: Int,
    animeName: String,
    viewModel: AnimeByIdViewModel,
    navController: NavController
) {
    val state = viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = animeId) {
        viewModel.getAnimeById(animeId)
    }
    Scaffold(topBar = {
        MainTopBar(title = animeName, navController = navController)
    }, content = { innerPadding ->
        when (state.value) {
            is Result.Loading -> { /* Mostrar indicador de carga */
                LoadingDialog(isLoading = true)
            }

            is Result.Success -> {
                val anime: AnimeDetailsModel =
                    (state.value as Result.Success<AnimeDetailsModel>).data
                AnimeDescriptionView(anime = anime, innerPadding = innerPadding)
            }

            is Result.Error -> {
                MessageDialog(
                    title = stringResource(R.string.no_results),
                    text = stringResource(R.string.no_anime_found),
                    showDialog = true,
                    secondaryButtonText = stringResource(R.string.retry),
                    onSecondaryButton = { viewModel.getAnimeById(animeId) }
                )
            }

            else -> {}
        }
    })
}

@Composable
@Preview
fun previewAnimeContent() {
    AnimeDescriptionView(anime = SampleFullAnimeModel)
}

@Composable
fun AnimeDescriptionView(innerPadding: PaddingValues? = null, anime: AnimeDetailsModel) {
    Column(
        modifier = Modifier
            .padding(
                top = innerPadding?.calculateTopPadding() ?: 0.dp,
                start = margin_medium,
                end = margin_medium
            )
            .verticalScroll(rememberScrollState())
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (nameText, stats, image, description) = createRefs()
            val showShimmer = remember { mutableStateOf(true) }

            Text(
                text = anime.name,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .constrainAs(nameText) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .padding(bottom = margin_medium, top = margin_medium)
            )
            AsyncImage(
                model = anime.imageBig,
                contentDescription = anime.imageMedium,
                onSuccess = { showShimmer.value = false },
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(nameText.bottom)
                        start.linkTo(parent.start)
                    }
                    .fillMaxWidth(0.6f)
                    .aspectRatio(2f / 3.2f)
                    .background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value))
            )
            Column(
                modifier = Modifier
                    .constrainAs(stats) {
                        top.linkTo(nameText.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(image.end, margin = margin_medium)
                        width = Dimension.fillToConstraints
                    }
                    .padding(horizontal = margin_xsmall)
            ) {
                StatsInColumntText(
                    title = stringResource(id = R.string.label_score),
                    value = anime.score.toString()
                )
                Spacer(modifier = Modifier.height(margin_medium))
                StatsInColumntText(
                    title = stringResource(id = R.string.label_ranked),
                    value = "#" + anime.ranked
                )
                Spacer(modifier = Modifier.height(margin_medium))
                StatsInColumntText(
                    title = stringResource(id = R.string.popularity),
                    value = "#" + anime.popularity
                )
                Spacer(modifier = Modifier.height(margin_medium))
                StatsInColumntText(
                    title = stringResource(id = R.string.members),
                    value = anime.members.toString()
                )
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(description) {
                    top.linkTo(image.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(top = margin_medium)) {
                if (anime.trailer != null){
                    Column {
                        Text(
                            text = stringResource(R.string.trailer),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Divider(
                            color = MaterialTheme.colorScheme.onPrimary,
                            thickness = 2.dp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        anime.trailer.let { url->
                            extractVideoId(url)?.let { videoId ->
                                YoutubePlayerWebView(
                                    videoEmedded = videoId
                                )
                            }
                        }
                    }
                }

//                anime.trailer?.let { Text(text = it) }

                Text(
                    text = stringResource(R.string.synopsis),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleMedium
                )
                Divider(
                    color = MaterialTheme.colorScheme.onPrimary,
                    thickness = 2.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = anime.synopsis,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}


@Composable
fun StatsInColumntText(
    title: String,
    value: String,
    backgroundColor: Color = MaterialTheme.colorScheme.primary
) {
    Column(
        modifier = Modifier
            .wrapContentSize(Alignment.Center)
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier
            .drawBehind {
                drawRoundRect(
                    cornerRadius = CornerRadius(20f, 20f),
                    color = backgroundColor,
                    style = Fill,
                    topLeft = Offset(0f, 0f),
                    size = Size(size.width, size.height)
                )
            }) {
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(margin_xsmall),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),

                )
        }
        Text(
            text = value,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
//        Divider(
//            color = MaterialTheme.colorScheme.onPrimary,
//            thickness = 2.dp,
//            modifier = Modifier.padding(vertical = 8.dp)
//        )
    }
}

@Composable
fun YoutubePlayerWebView(videoEmedded: String) {
    val youTubePlayer = remember { mutableStateOf<YouTubePlayer?>(null) }

    AndroidView(
        factory = { context ->
            YouTubePlayerView(context).apply {
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(player: YouTubePlayer) {
                        youTubePlayer.value = player
                        videoEmedded.let { player.loadVideo(it, 0f) }
                    }
                })
            }
        },
        modifier = Modifier.fillMaxWidth(),
        update = {
        }
    )
}

fun extractVideoId(url: String): String? {
    val startIndex = url.indexOf("embed/") + 6
    val endIndex = url.indexOf("?")
    return if (startIndex >= 0 && endIndex >= 0) {
        url.substring(startIndex, endIndex)
    } else {
        null
    }
}