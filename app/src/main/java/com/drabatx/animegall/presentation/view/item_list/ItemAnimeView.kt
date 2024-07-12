package com.drabatx.animegall.presentation.view.item_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.drabatx.animegall.presentation.model.AnimeItemList
import com.drabatx.animegall.presentation.theme.margin_small
import com.drabatx.animegall.presentation.theme.margin_xsmall
import com.drabatx.animegall.presentation.widgets.AutoResizeText
import com.drabatx.animegall.presentation.widgets.FontSizeRange
import com.drabatx.animegall.presentation.widgets.RatingBar

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

                AutoResizeText(
                    text = animeItem.name,
                    maxLines = 2,
                    fontSizeRange = FontSizeRange(min = 4.sp, max = 24.sp),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .constrainAs(nameText) {
                            top.linkTo(scoreText.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                )

                if (animeItem.year > 1900) {
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