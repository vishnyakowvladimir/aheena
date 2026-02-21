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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
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

@OptIn(ExperimentalMotionApi::class)
@Composable
fun MotionLayoutScreen() {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val topFraction = 0.1f
    val middleFraction = 0.5f
    val bottomFraction = 0.8f
    val progressRange = bottomFraction - topFraction
    val initialProgress = (middleFraction - topFraction) / progressRange
    val minImageAlpha = 0.2f
    val minListAlpha = 0.2f

    val screenHeightPx = remember(configuration, density) {
        with(density) { configuration.screenHeightDp.dp.toPx() }
    }
    val imageHeightDp = remember(configuration, density) {
        with(density) { (configuration.screenHeightDp.dp * bottomFraction) }
    }

    var progress by remember { mutableFloatStateOf(initialProgress) }

    val sheetFraction = (topFraction + progressRange * progress).coerceIn(topFraction, bottomFraction)
    val imageAlpha = if (sheetFraction <= middleFraction) {
        val t = (sheetFraction - topFraction) / (middleFraction - topFraction)
        (minImageAlpha + (1f -minImageAlpha) * t).coerceIn(minImageAlpha, 1f)
    } else {
        1f
    }
    val listAlpha = if (sheetFraction >= middleFraction) {
        val t = (sheetFraction - middleFraction) / (bottomFraction - middleFraction)
        (1f - (1f - minListAlpha) * t).coerceIn(minListAlpha, 1f)
    } else {
        1f
    }

    val startConstraints = remember {
        ConstraintSet {
            val bottomSheet = createRefFor("bottomSheet")
            val sheetGuide = createGuidelineFromTop(topFraction)

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
            val bottomSheet = createRefFor("bottomSheet")
            val sheetGuide = createGuidelineFromTop(bottomFraction)

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AsyncImage(
            model = "https://s4.fotokto.ru/photo/full/869/8696410.jpg",
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeightDp)
                .alpha(imageAlpha),
            contentScale = ContentScale.Crop
        )

        MotionLayout(
            start = startConstraints,
            end = endConstraints,
            progress = progress,
            modifier = Modifier.fillMaxSize()
        ) {
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
                            .fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 100.dp)
                    ) {
                        items(5) { rowIndex ->
                            Text(
                                text = "Образ ${rowIndex + 1}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .alpha(listAlpha)
                            )
                            LazyRow(
                                modifier = Modifier.alpha(listAlpha),
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(4) {
                                    ProductCard(contentAlpha = listAlpha)
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
}

private val productImages = listOf(
    "https://s4.fotokto.ru/photo/full/869/8695883.jpg",
    "https://s3.fotokto.ru/photo/full/869/8695882.jpg",
    "https://s2.fotokto.ru/photo/full/867/8675155.jpg",
    "https://s3.fotokto.ru/photo/full/869/8696169.jpg"
)

@Composable
fun ProductCard(contentAlpha: Float = 1f, index: Int = 0) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .alpha(contentAlpha),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = productImages[index % productImages.size],
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
                model = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=100",
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
                Text("Купить", color = Color.White, fontSize = 8.sp)
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C27B0)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("В корзину", color = Color.White, fontSize = 8.sp)
            }
        }
    }
}
