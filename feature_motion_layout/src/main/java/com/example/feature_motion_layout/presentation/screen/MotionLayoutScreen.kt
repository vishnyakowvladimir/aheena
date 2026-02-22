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
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
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

    val screenHeightPx = remember(configuration, density) {
        with(density) { configuration.screenHeightDp.dp.toPx() }
    }

    // Используем обычный state для мгновенной реакции на движение пальца
    var progress by remember { mutableFloatStateOf(initialProgress) }

    val scene = remember {
        MotionScene {
            val imageId = createRefFor("imageId")
            val bottomSheet = createRefFor("bottomSheet")
            val bottomPlate = createRefFor("bottomPlate")
            val listContent = createRefFor("listContent")

            val start = constraintSet("start") {
                constrain(bottomSheet) {
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                    top.linkTo(parent.top, 30.dp)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                // listContent теперь отдельный элемент, привязанный к sheet
                constrain(listContent) {
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                    top.linkTo(bottomSheet.top, 40.dp) // Отступ под "ручку"
                    bottom.linkTo(bottomSheet.bottom)
                    start.linkTo(bottomSheet.start)
                    end.linkTo(bottomSheet.end)
                    alpha = 1f
                }
                constrain(imageId) {
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                    top.linkTo(parent.top)
                    bottom.linkTo(bottomPlate.top, 80.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    alpha = 0.2f
                }
                constrain(bottomPlate) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            }

            val end = constraintSet("end") {
                constrain(bottomSheet) {
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                    top.linkTo(bottomPlate.top, (-40).dp)
                    bottom.linkTo(parent.bottom)
                    this.start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                constrain(listContent) {
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                    top.linkTo(bottomSheet.top, 40.dp)
                    bottom.linkTo(bottomSheet.bottom)
                    this.start.linkTo(bottomSheet.start)
                    end.linkTo(bottomSheet.end)
                    alpha = 0.2f
                }
                constrain(imageId) {
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                    top.linkTo(parent.top)
                    bottom.linkTo(bottomPlate.top, 80.dp)
                    this.start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    alpha = 1f
                }
                constrain(bottomPlate) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    bottom.linkTo(parent.bottom)
                    this.start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            }

            transition(start, end, "default") {
                // Промежуточные состояния (KeyFrames)
                keyAttributes(imageId) {
                    frame(0) { alpha = 0.2f }
                }
                keyAttributes(imageId) {
                    frame(30) { alpha = 1f }
                }

                keyAttributes(imageId) {
                    frame(50) { alpha = 1f }
                }
                keyAttributes(imageId) {
                    frame(100) { alpha = 1f }
                }

                keyAttributes(listContent) {
                    frame(0) { alpha = 1f }
                }
                keyAttributes(listContent) {
                    frame(50) { alpha = 1f }
                }

                keyAttributes(listContent) {
                    frame(70) { alpha = 1f }
                }
                keyAttributes(listContent) {
                    frame(100) { alpha = 0.2f }
                }
            }
        }
    }

    MotionLayout(
        motionScene = scene,
        progress = progress,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AsyncImage(
            model = "https://s4.fotokto.ru/photo/full/869/8696410.jpg",
            contentDescription = null,
            modifier = Modifier.layoutId("imageId"),
            contentScale = ContentScale.Crop
        )

        // Фон шторки и "ручка"
        Box(
            modifier = Modifier
                .layoutId("bottomSheet")
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White)
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
                    .align(Alignment.TopCenter)
            )
        }

        // Контент списка (вынесен в корень MotionLayout для работы alpha)
        Box(
            modifier = Modifier.layoutId("listContent")
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
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
        }

        BottomPlate(
            modifier = Modifier
                .layoutId("bottomPlate")
                .background(Color.White)
        )
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
            Text(
                text = "2 141 ₽",
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
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
