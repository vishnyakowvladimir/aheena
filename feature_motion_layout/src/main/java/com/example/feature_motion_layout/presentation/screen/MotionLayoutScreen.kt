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
import androidx.compose.runtime.derivedStateOf
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
fun MotionLayoutScreen() {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()

    val topFractionLimit = 0.1f
    val defaultBottomFraction = 0.8f

    val screenHeightPx = remember(configuration, density) {
        with(density) { configuration.screenHeightDp.dp.toPx() }
    }

    val listState = rememberLazyListState()

    // 1. Стабильно определяем высоту контента (независимо от текущего скролла)
    val listContentHeightPx by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visibleItems = layoutInfo.visibleItemsInfo
            if (visibleItems.isEmpty()) {
                -1f
            } else {
                val firstItem = visibleItems.first()
                val lastItem = visibleItems.last()
                // Считаем список коротким, если видны и первый, и последний элементы
                val isShort = firstItem.index == 0 && lastItem.index == layoutInfo.totalItemsCount - 1

                if (isShort) {
                    // Высота контента = (расстояние между началом первого и концом последнего) + отступы
                    val itemsHeight = (lastItem.offset + lastItem.size) - firstItem.offset
                    (layoutInfo.beforeContentPadding + itemsHeight + layoutInfo.afterContentPadding).toFloat()
                } else {
                    -1f
                }
            }
        }
    }

    // 2. Рассчитываем динамические пороги раскрытия
    val effectiveTopFraction = remember(listContentHeightPx, screenHeightPx) {
        if (listContentHeightPx > 0) {
            val handlePaddingPx = with(density) { 16.dp.toPx() }
            val neededFraction = (listContentHeightPx + handlePaddingPx) / screenHeightPx
            // Раскрываем ровно под контент, но не выше лимита 0.1
            (1f - neededFraction).coerceAtLeast(topFractionLimit)
        } else {
            topFractionLimit
        }
    }

    val effectiveBottomFraction = remember(effectiveTopFraction) {
        defaultBottomFraction.coerceAtLeast(effectiveTopFraction)
    }

    val progressRange = remember(effectiveTopFraction, effectiveBottomFraction) {
        (effectiveBottomFraction - effectiveTopFraction).coerceAtLeast(0.01f)
    }

    var progress by remember { mutableFloatStateOf(0.5f) }
    val settleJob = remember { mutableStateOf<Job?>(null) }
    val snapPoints = listOf(0f, 0.5f, 1f)

    // 3. Функция плавной "доводки" (snapping)
    fun startSettle(velocity: Float) {
        settleJob.value?.cancel()
        settleJob.value = coroutineScope.launch {
            val current = progress
            val target = when {
                velocity > 1000 -> snapPoints.filter { it > current + 0.05f }.minOrNull() ?: 1f
                velocity < -1000 -> snapPoints.filter { it < current - 0.05f }.maxOrNull() ?: 0f
                else -> snapPoints.minByOrNull { abs(it - current) } ?: 0f
            }
            if (target == current && abs(velocity) < 100) return@launch

            Animatable(current).animateTo(
                targetValue = target,
                initialVelocity = velocity / (screenHeightPx * progressRange),
                animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = 400f)
            ) { progress = value }
        }
    }

    val nestedScrollConnection = remember(screenHeightPx, progressRange, listState) {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (source == NestedScrollSource.Drag) settleJob.value?.cancel()
                val delta = available.y

                // Тянем вверх - раскрываем шторку
                if (delta < 0 && progress > 0f) {
                    val progressDelta = delta / (screenHeightPx * progressRange)
                    val oldProgress = progress
                    progress = (progress + progressDelta).coerceIn(0f, 1f)
                    return Offset(0f, (progress - oldProgress) * (screenHeightPx * progressRange))
                }

                // Тянем вниз - сворачиваем шторку (если список в самом верху)
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

    val topOffsetDp = (effectiveTopFraction * configuration.screenHeightDp).toInt()
    val bottomOffsetDp = (effectiveBottomFraction * configuration.screenHeightDp).toInt()

    val scene = MotionScene(content = """
    {
      "ConstraintSets": {
        "start": {
          "bottomSheet": { "width": "spread", "height": "spread", "top": ["parent", "top", $topOffsetDp], "bottom": ["parent", "bottom"], "start": ["parent", "start"], "end": ["parent", "end"] },
          "listContent": { "width": "spread", "height": "spread", "top": ["bottomSheet", "top", 16], "bottom": ["bottomSheet", "bottom"], "start": ["bottomSheet", "start"], "end": ["bottomSheet", "end"] },
          "listContentBackground": { "width": "spread", "height": "spread", "top": ["bottomSheet", "top"], "bottom": ["bottomSheet", "bottom"], "start": ["bottomSheet", "start"], "end": ["bottomSheet", "end"] },
          "imageId": { "width": "spread", "height": "spread", "top": ["parent", "top"], "bottom": ["bottomPlate", "top", 80], "start": ["parent", "start"], "end": ["parent", "end"] },
          "bottomPlate": { "width": "spread", "height": "wrap", "bottom": ["parent", "bottom"], "start": ["parent", "start"], "end": ["parent", "end"] }
        },
        "end": {
          "bottomSheet": { "width": "spread", "height": "spread", "top": ["parent", "top", $bottomOffsetDp], "bottom": ["parent", "bottom"], "start": ["parent", "start"], "end": ["parent", "end"] },
          "listContent": { "width": "spread", "height": "spread", "top": ["bottomSheet", "top", 16], "bottom": ["bottomSheet", "bottom"], "start": ["bottomSheet", "start"], "end": ["bottomSheet", "end"] },
          "listContentBackground": { "width": "spread", "height": "spread", "top": ["bottomSheet", "top"], "bottom": ["bottomSheet", "bottom"], "start": ["bottomSheet", "start"], "end": ["bottomSheet", "end"] },
          "imageId": { "width": "spread", "height": "spread", "top": ["parent", "top"], "bottom": ["bottomPlate", "top", 80], "start": ["parent", "start"], "end": ["parent", "end"] },
          "bottomPlate": { "width": "spread", "height": "wrap", "bottom": ["parent", "bottom"], "start": ["parent", "start"], "end": ["parent", "end"] }
        }
      },
      "Transitions": {
        "default": {
          "from": "start", "to": "end",
          "KeyFrames": {
            "KeyAttributes": [
              { "target": ["imageId"], "frames": [0, 50, 100], "alpha": [0.2, 1, 1] },
              { "target": ["listContent"], "frames": [0, 50, 100], "alpha": [1, 1, 0.2] }
            ]
          }
        }
      }
    }
    """.trimIndent())

    MotionLayout(
        motionScene = scene,
        progress = progress,
        modifier = Modifier.fillMaxSize().background(Color.White)
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
                .pointerInput(Unit) {
                    val tracker = VelocityTracker()
                    detectVerticalDragGestures(
                        onVerticalDrag = { change, dragAmount ->
                            settleJob.value?.cancel()
                            tracker.addPointerInputChange(change)
                            val delta = dragAmount / (screenHeightPx * progressRange)
                            progress = (progress + delta).coerceIn(0f, 1f)
                        },
                        onDragEnd = {
//                            startSettle(tracker.calculateVelocity().y)
                        }
                    )
                }
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp)
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
                .nestedScroll(nestedScrollConnection)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(1) { rowIndex ->
                    Text(text = "Образ ${rowIndex + 1}", modifier = Modifier.padding(16.dp))
                    LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
                        items(8) { ProductCard() }
                    }
                }
            }
        }

        BottomPlate()
    }
}

@Composable
private fun ProductCard(contentAlpha: Float = 1f) {
    Card(
        modifier = Modifier.alpha(contentAlpha),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(6.dp)
    ) {
        Column(modifier = Modifier.padding(2.dp)) {
            AsyncImage(
                model = "https://basket-27.wbbasket.ru/vol4963/part496350/496350181/images/hq/1.webp",
                contentDescription = null,
                modifier = Modifier.width(90.dp).height(120.dp).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "2 141 ₽", fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text(text = "Товар для примера", fontSize = 10.sp, maxLines = 1)
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C27B0))
            ) { Text("В корзину", fontSize = 10.sp) }
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
            modifier = Modifier.size(30.dp, 40.dp).clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "2 456 ₽", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(text = "Послезавтра", fontSize = 12.sp, color = Color.Gray)
        }
        Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)), shape = RoundedCornerShape(8.dp)) { Text("Купить", fontSize = 12.sp) }
        Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C27B0)), shape = RoundedCornerShape(8.dp)) { Text("В корзину", fontSize = 12.sp) }
    }
}
