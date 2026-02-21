package com.example.feature_motion_layout.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalMotionApi::class)
@Composable
fun MotionLayoutScreen() {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val screenHeightPx = remember(configuration, density) {
        with(density) { configuration.screenHeightDp.dp.toPx() }
    }

    val topFraction = 0.2f
    val middleFraction = 0.5f
    val bottomFraction = 1f
    val progressRange = bottomFraction - topFraction
    val initialProgress = (middleFraction - topFraction) / progressRange

    var progress by remember { mutableFloatStateOf(initialProgress) }

    val sheetFraction = (topFraction + progressRange * progress).coerceIn(topFraction, bottomFraction)
    val imageAlpha = if (sheetFraction <= middleFraction) {
        val t = (sheetFraction - topFraction) / (middleFraction - topFraction)
        (0.2f + (1f - 0.2f) * t).coerceIn(0.2f, 1f)
    } else {
        1f
    }
    val listAlpha = if (sheetFraction >= middleFraction) {
        val t = (sheetFraction - middleFraction) / (bottomFraction - middleFraction)
        (1f - 0.5f * t).coerceIn(0.5f, 1f)
    } else {
        1f
    }

    val startConstraints = remember {
        ConstraintSet {
            val mainImage = createRefFor("mainImage")
            val bottomSheet = createRefFor("bottomSheet")
            val sheetGuide = createGuidelineFromTop(topFraction)

            constrain(mainImage) {
                width = Dimension.fillToConstraints
                height = Dimension.percent(0.75f)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }

            constrain(bottomSheet) {
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
                top.linkTo(sheetGuide)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        }
    }

    val endConstraints = remember {
        ConstraintSet {
            val mainImage = createRefFor("mainImage")
            val bottomSheet = createRefFor("bottomSheet")
            val sheetGuide = createGuidelineFromTop(bottomFraction)

            constrain(mainImage) {
                width = Dimension.fillToConstraints
                height = Dimension.percent(0.75f)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }

            constrain(bottomSheet) {
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
                top.linkTo(sheetGuide)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        }
    }

    MotionLayout(
        start = startConstraints,
        end = endConstraints,
        progress = progress,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE91E63))
                .padding(12.dp)
                .zIndex(2f)
        ) {
            Text(
                text = "DEBUG: MotionLayoutScreen visible",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data("https://images.unsplash.com/photo-1524504388940-b1c1722653e1?q=80&w=1000&auto=format&fit=crop")
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .layoutId("mainImage")
                .fillMaxWidth()
                .alpha(imageAlpha),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .layoutId("bottomSheet")
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White)
                .zIndex(1f)
                .pointerInput(Unit) {
                    detectVerticalDragGestures { change, dragAmount ->
                        change.consume()
                        val delta = dragAmount / (screenHeightPx * progressRange)
                        progress = (progress + delta).coerceIn(0f, 1f)
                    }
                }
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .size(40.dp, 4.dp)
                    .background(Color.LightGray, RoundedCornerShape(2.dp))
                    .align(Alignment.CenterHorizontally)
            )

            Box(modifier = Modifier.weight(1f)) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(listAlpha),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    items(5) { rowIndex ->
                        Text(
                            text = "Образ ${rowIndex + 1}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp)
                        )
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(4) {
                                ProductCard()
                            }
                        }
                    }
                }

                BottomPlate(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ProductCard() {
    Card(
        modifier = Modifier.width(160.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1523275335684-37898b6baf30?q=80&w=200&auto=format&fit=crop",
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "2 141 ₽", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = "Товар для примера", fontSize = 8.sp, maxLines = 1)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C27B0))
            ) {
                Text("В корзину", fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun BottomPlate(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1524504388940-b1c1722653e1?q=80&w=1000&auto=format&fit=crop",
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "2 456 ₽", fontSize = 8.sp, fontWeight = FontWeight.Bold)
                Text(text = "Послезавтра", fontSize = 8.sp, color = Color.Gray)
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Купить", color = Color.White)
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C27B0)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("В корзину", color = Color.White)
            }
        }
    }
}
