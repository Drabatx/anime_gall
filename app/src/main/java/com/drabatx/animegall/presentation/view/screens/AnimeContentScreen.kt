package com.drabatx.animegall.presentation.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.drabatx.animegall.R
import com.drabatx.animegall.presentation.model.FullAnimeModel
import com.drabatx.animegall.presentation.model.SampleFullAnimeModel
import com.drabatx.animegall.presentation.view.theme.margin_medium
import com.drabatx.animegall.presentation.view.theme.margin_xsmall
import com.drabatx.animegall.presentation.view.viewmodels.AnimeByIdViewModel
import com.drabatx.animegall.presentation.widgets.shimmerBrush


@Composable
fun AnimeContentScreen(viewModel: AnimeByIdViewModel, navController: NavController) {

}

@Composable
@Preview
fun previewAnimeContent() {
    AnimeDescriptionView(SampleFullAnimeModel)
}

@Composable
fun AnimeDescriptionView(anime: FullAnimeModel) {
    Scaffold(
        modifier = Modifier.padding(
            horizontal = margin_medium,
            vertical = margin_medium
        )
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (nameText, stats, image, description) = createRefs()
            val showShimmer = remember { mutableStateOf(true) }

            Text(text = anime.name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .constrainAs(nameText) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(bottom = margin_medium)
            )
            AsyncImage(
                model = anime.imageBig,
                contentDescription = anime.imageMedium,
                onSuccess = { showShimmer.value = false },
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(nameText.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(end = margin_medium)
                    .fillMaxWidth(0.5f)
                    .aspectRatio(2f / 3f)
                    .background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value))
            )
            Box(modifier = Modifier
                .constrainAs(stats) {
                    top.linkTo(nameText.bottom)
                    start.linkTo(image.end)
                    end.linkTo(parent.end)
                }) {
                Column {
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
                }

            }

            Text(
                text = anime.synopsis,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(description) {
                        top.linkTo(image.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(top = margin_medium)
            )
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
        modifier = Modifier.wrapContentSize(Alignment.Center) // Centra el contenido
    ) {
        Box(modifier = Modifier
            .fillMaxWidth(0.4f)
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
                    .padding(margin_xsmall)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),

                )
        }
        Text(
            text = value,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}