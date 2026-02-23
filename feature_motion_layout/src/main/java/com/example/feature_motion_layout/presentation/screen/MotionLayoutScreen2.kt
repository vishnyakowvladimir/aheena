package com.example.feature_motion_layout.presentation.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.input.pointer.util.addPointerInputChange
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import coil.compose.AsyncImage
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.abs

@OptIn(ExperimentalMotionApi::class)
@Composable
fun MotionLayoutScreen2() {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()

    val topFraction = 0.1f
    val middleFraction = 0.5f
    val bottomFraction = 0.8f
    val progressRange = bottomFraction - topFraction
    val initialProgress = (middleFraction - topFraction) / progressRange

    val screenHeightPx = remember(configuration, density) {
        with(density) { configuration.screenHeightDp.dp.toPx() }
    }

    val listState = rememberLazyListState()

    // Используем обычный mutableState для мгновенного (синхронного) обновления во время свайпа
    var progress by remember { mutableFloatStateOf(initialProgress) }

    val snapPoints = listOf(0f, 0.5f, 1f)
    val settleJob = remember { mutableStateOf<Job?>(null) }

    // Функция плавной доводки (snapping), вызывается только при отпускании пальца
// Внутри MotionLayoutScreen

//    fun startSettle(velocity: Float) {
//        settleJob.value?.cancel() // Отменяем старую анимацию, если она есть
//        settleJob.value = coroutineScope.launch {
//            val current = progress
//            val target = when {
//                velocity > 1000 -> snapPoints.filter { it > current }.minOrNull() ?: 1f
//                velocity < -1000 -> snapPoints.filter { it < current }.maxOrNull() ?: 0f
//                else -> snapPoints.minByOrNull { kotlin.math.abs(it - current) } ?: 0f
//            }
//            if (target == current && kotlin.math.abs(velocity) < 100) return@launch
//
//            Animatable(current).animateTo(
//                targetValue = target,
//                initialVelocity = velocity / (screenHeightPx * progressRange),
//                animationSpec = spring(
//                    dampingRatio = Spring.DampingRatioNoBouncy,
//                    stiffness = 800f
//                )
//            ) {
//                progress = value
//            }
//        }
//    }

    val nestedScrollConnection = remember(screenHeightPx, progressRange, listState) {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // При любом новом свайпе отменяем автоматическую доводку
                if (source == NestedScrollSource.Drag) {
                    settleJob.value?.cancel()
                }

                val delta = available.y

                // Раскрываем (двигаем вверх)
                if (delta < 0 && progress > 0f) {
                    val progressDelta = delta / (screenHeightPx * progressRange)
                    val oldProgress = progress
                    progress = (progress + progressDelta).coerceIn(0f, 1f)
                    return Offset(0f, (progress - oldProgress) * (screenHeightPx * progressRange))
                }

                // Сворачиваем (двигаем вниз) - используем canScrollBackward для надежности
                val isAtTop = !listState.canScrollBackward
                if (delta > 0 && isAtTop && progress < 1f) {
                    val progressDelta = delta / (screenHeightPx * progressRange)
                    val oldProgress = progress
                    progress = (progress + progressDelta).coerceIn(0f, 1f)
                    return Offset(0f, (progress - oldProgress) * (screenHeightPx * progressRange))
                }
                return Offset.Zero
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
//                startSettle(available.y)
                return available
            }
        }
    }

    // Описание сцены через JSON
    val scene =
        MotionScene(
            content = """
            {
              "ConstraintSets": {
                "start": {
                  "bottomSheet": {
                    "width": "spread", 
                    "height": "spread",
                    "top": ["parent", "top", 30], 
                    "bottom": ["parent", "bottom"],
                    "start": ["parent", "start"], 
                    "end": ["parent", "end"]
                  },
                  "listContent": {
                    "width": "spread", 
                    "height": "spread",
                    "top": ["bottomSheet", "top", 16], 
                    "bottom": ["bottomSheet", "bottom"],
                    "start": ["bottomSheet", "start"], 
                    "end": ["bottomSheet", "end"]
                  },
                  "listContentBackground": {
                    "width": "spread", 
                    "height": "spread",
                    "top": ["listContent", "top"], 
                    "bottom": ["listContent", "bottom"],
                    "start": ["listContent", "start"], 
                    "end": ["listContent", "end"],
                  },
                  "imageId": {
                    "width": "spread", 
                    "height": "spread",
                    "top": ["parent", "top"], 
                    "bottom": ["bottomPlate", "top", 80],
                    "start": ["parent", "start"], 
                    "end": ["parent", "end"]
                  },
                  "bottomPlate": {
                    "width": "spread", 
                    "height": "wrap",
                    "bottom": ["parent", "bottom"],
                    "start": ["parent", "start"], 
                    "end": ["parent", "end"]
                  }
                },
                "end": {
                  "bottomSheet": {
                    "width": "spread", 
                    "height": "spread",
                    "top": ["imageId", "bottom", -40], 
                    "bottom": ["parent", "bottom"],
                    "start": ["parent", "start"], 
                    "end": ["parent", "end"]
                  },
                  "listContent": {
                    "width": "spread", 
                    "height": "spread",
                    "top": ["bottomSheet", "top", 16], 
                    "bottom": ["bottomSheet", "bottom"],
                    "start": ["bottomSheet", "start"], 
                    "end": ["bottomSheet", "end"]
                  },
                  "listContentBackground": {
                    "width": "spread", 
                    "height": "spread",
                    "top": ["listContent", "top"], 
                    "bottom": ["listContent", "bottom"],
                    "start": ["listContent", "start"], 
                    "end": ["listContent", "end"],
                  },
                  "imageId": {
                    "width": "spread", 
                    "height": "spread",
                    "top": ["parent", "top"], 
                    "bottom": ["bottomPlate", "top", 80],
                    "start": ["parent", "start"], 
                    "end": ["parent", "end"]
                  },
                  "bottomPlate": {
                    "width": "spread", 
                    "height": "wrap",
                    "bottom": ["parent", "bottom"],
                    "start": ["parent", "start"], 
                    "end": ["parent", "end"]
                  }
                }
              },
              "Transitions": {
                "default": {
                  "from": "start",
                  "to": "end",
                  "KeyFrames": {
                    "KeyAttributes": [
                      {
                        "target": ["imageId"],
                        "frames": [0],
                        "alpha": [0.2]
                      },  
                      {
                        "target": ["imageId"],
                        "frames": [50],
                        "alpha": [1]
                      }, 
                      {
                        "target": ["imageId"],
                        "frames": [100],
                        "alpha": [1]
                      }, 
                      {
                        "target": ["listContent"],
                        "frames": [0],
                        "alpha": [1]
                      },
                                            {
                        "target": ["listContent"],
                        "frames": [50],
                        "alpha": [1]
                      },
                                            {
                        "target": ["listContent"],
                        "frames": [100],
                        "alpha": [0.2]
                      }
                    ]
                  }
                }
              }
            }
        """.trimIndent()
        )


    MotionLayout(
        motionScene = scene,
        progress = progress,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AsyncImage(
            model = "https://basket-34.wbbasket.ru/vol7328/part732854/732854608/images/big/1.webp",
            contentDescription = null,
            modifier = Modifier.layoutId("imageId"),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .layoutId("bottomSheet")
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
//                .background(Color.White)
                .pointerInput(Unit) {
                    val velocityTracker = VelocityTracker()
                    detectVerticalDragGestures(
                        onVerticalDrag = { change, dragAmount ->
                            settleJob.value?.cancel() // Важно!
                            velocityTracker.addPointerInputChange(change)
                            val delta = dragAmount / (screenHeightPx * progressRange)
                            progress = (progress + delta).coerceIn(0f, 1f)
                        },
                        onDragEnd = {
                            val velocity = velocityTracker.calculateVelocity().y
//                            startSettle(velocity)
                        }
                    )
                }
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp, 4.dp)
                    .background(Color.LightGray, RoundedCornerShape(2.dp))
                    .align(Alignment.TopCenter)
            )
        }

        Box(
            modifier = Modifier
                .layoutId("listContentBackground")
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White)
        )

        Box(
            modifier = Modifier
                .layoutId("listContent")
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White)
                .nestedScroll(nestedScrollConnection)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(5) { rowIndex ->
                    Text(text = "Образ ${rowIndex + 1}", modifier = Modifier.padding(16.dp))
                    LazyRow { items(8) { ProductCard() } }
                }
            }
        }

        BottomPlate()
    }
}

private val productImages = listOf(
    "https://s4.fotokto.ru/photo/full/869/8695883.jpg",
    "https://s3.fotokto.ru/photo/full/869/8695882.jpg",
    "https://s2.fotokto.ru/photo/full/867/8675155.jpg",
    "https://s3.fotokto.ru/photo/full/869/8696169.jpg"
)

@Composable
private fun ProductCard(contentAlpha: Float = 1f, index: Int = 0) {
    Card(
        modifier = Modifier
            .alpha(contentAlpha),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(6.dp)
    ) {
        Column(modifier = Modifier.padding(2.dp)) {
            AsyncImage(
                model = "https://basket-27.wbbasket.ru/vol4963/part496350/496350181/images/hq/1.webp",
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .width(90.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "2 141 ₽",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(text = "Товар для примера", fontSize = 10.sp, maxLines = 1)
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C27B0))
            ) {
                Text("В корзину", fontSize = 10.sp)
            }
        }
    }
}

@Composable
private fun BottomPlate() {
    Row(
        modifier = Modifier
            .layoutId("bottomPlate")
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(Color.White)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = "https://s4.fotokto.ru/photo/full/869/8696410.jpg",
            contentDescription = null,
            modifier = Modifier
                .height(40.dp)
                .width(30.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(text = "2 456 ₽", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(text = "Послезавтра", fontSize = 12.sp, color = Color.Gray)
        }

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Купить", color = Color.White, fontSize = 12.sp)
        }

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C27B0)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("В корзину", color = Color.White, fontSize = 12.sp)
        }
    }
}
