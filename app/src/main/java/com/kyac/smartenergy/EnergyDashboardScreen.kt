package com.kyac.smartenergy

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun EnergyDashboardScreen() {
    var selectedTab by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A1929),
                        Color(0xFF0D1F33),
                        Color(0xFF0A1929)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Status bar spacer
            Spacer(modifier = Modifier.height(40.dp))

            // Main content
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Animated Rings
                    AnimatedEnergyRings()

                    Spacer(modifier = Modifier.height(32.dp))

                    // Smart Assistant Card
                    SmartAssistantCard()
                }
            }

            // Bottom Navigation
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    }
}

@Composable
fun AnimatedEnergyRings() {
    // Animation states for rotation
    val infiniteTransition = rememberInfiniteTransition(label = "ring_rotation")
    
    // Outer ring - slow rotation (20 seconds per rotation)
    val outerRingRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "outer_ring"
    )
    
    // Middle ring - medium rotation (15 seconds per rotation)
    val middleRingRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(15000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "middle_ring"
    )
    
    // Inner ring - faster rotation (10 seconds per rotation)
    val innerRingRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "inner_ring"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(400.dp)
        ) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val center = Offset(centerX, centerY)

            // Outer Ring (Cyan) - Full circle
            rotate(outerRingRotation, pivot = center) {
                val outerRadius = 180f
                val outerGradient = Brush.sweepGradient(
                    colors = listOf(
                        Color(0xFF4DD0E1),
                        Color(0xFF26C6DA),
                        Color(0xFF00BCD4),
                        Color(0xFF4DD0E1)
                    ),
                    center = center
                )
                drawCircle(
                    brush = outerGradient,
                    radius = outerRadius,
                    center = center,
                    style = Stroke(width = 8f, cap = StrokeCap.Round)
                )
                
                // Glow effect
                drawCircle(
                    color = Color(0xFF4DD0E1).copy(alpha = 0.3f),
                    radius = outerRadius,
                    center = center,
                    style = Stroke(width = 16f, cap = StrokeCap.Round)
                )
                
                // Particles on outer ring
                for (i in 0..8) {
                    val angle = (outerRingRotation + i * 40) * PI / 180
                    val x = centerX + outerRadius * cos(angle).toFloat()
                    val y = centerY + outerRadius * sin(angle).toFloat()
                    drawCircle(
                        color = Color(0xFF4DD0E1).copy(alpha = 0.6f),
                        radius = 2f,
                        center = Offset(x, y)
                    )
                }
            }

            // Middle Ring (Orange) - 80% arc
            rotate(middleRingRotation, pivot = center) {
                val middleRadius = 135f
                val sweepAngle = 360f * 0.8f
                val orangeGradient = Brush.sweepGradient(
                    colors = listOf(
                        Color(0xFFFFB74D),
                        Color(0xFFFFA726),
                        Color(0xFFFF9800),
                        Color(0xFFFFB74D)
                    ),
                    center = center
                )
                drawArc(
                    brush = orangeGradient,
                    startAngle = -90f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = 7f, cap = StrokeCap.Round),
                    topLeft = Offset(centerX - middleRadius, centerY - middleRadius),
                    size = androidx.compose.ui.geometry.Size(middleRadius * 2, middleRadius * 2)
                )
                
                // Glow effect
                drawArc(
                    color = Color(0xFFFFB74D).copy(alpha = 0.3f),
                    startAngle = -90f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = 14f, cap = StrokeCap.Round),
                    topLeft = Offset(centerX - middleRadius, centerY - middleRadius),
                    size = androidx.compose.ui.geometry.Size(middleRadius * 2, middleRadius * 2)
                )
                
                // Particles on middle ring
                for (i in 0..6) {
                    val angle = (-90 + middleRingRotation + i * 45) * PI / 180
                    val x = centerX + middleRadius * cos(angle).toFloat()
                    val y = centerY + middleRadius * sin(angle).toFloat()
                    drawCircle(
                        color = Color(0xFFFFB74D).copy(alpha = 0.6f),
                        radius = 1.8f,
                        center = Offset(x, y)
                    )
                }
            }

            // Inner Ring (Red/Pink) - 40% arc
            rotate(innerRingRotation, pivot = center) {
                val innerRadius = 90f
                val sweepAngle = 360f * 0.4f
                val redGradient = Brush.sweepGradient(
                    colors = listOf(
                        Color(0xFFFF4081),
                        Color(0xFFE91E63),
                        Color(0xFFC2185B),
                        Color(0xFFFF4081)
                    ),
                    center = center
                )
                drawArc(
                    brush = redGradient,
                    startAngle = -90f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = 6f, cap = StrokeCap.Round),
                    topLeft = Offset(centerX - innerRadius, centerY - innerRadius),
                    size = androidx.compose.ui.geometry.Size(innerRadius * 2, innerRadius * 2)
                )
                
                // Glow effect
                drawArc(
                    color = Color(0xFFFF4081).copy(alpha = 0.3f),
                    startAngle = -90f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = 12f, cap = StrokeCap.Round),
                    topLeft = Offset(centerX - innerRadius, centerY - innerRadius),
                    size = androidx.compose.ui.geometry.Size(innerRadius * 2, innerRadius * 2)
                )
                
                // Particles on inner ring
                for (i in 0..4) {
                    val angle = (-90 + innerRingRotation + i * 35) * PI / 180
                    val x = centerX + innerRadius * cos(angle).toFloat()
                    val y = centerY + innerRadius * sin(angle).toFloat()
                    drawCircle(
                        color = Color(0xFFFF4081).copy(alpha = 0.6f),
                        radius = 1.5f,
                        center = Offset(x, y)
                    )
                }
            }
        }

        // Center content with text overlays
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.offset(y = (-40).dp)
        ) {
            // Outer ring label
            Text(
                text = "OUTER RING",
                fontSize = 10.sp,
                color = Color(0xFF4DD0E1),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "1,260",
                fontSize = 42.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "kWh",
                fontSize = 18.sp,
                color = Color.White.copy(alpha = 0.7f)
            )
            Text(
                text = "YILLIK KALAN",
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Middle ring label
            Text(
                text = "MIDDLE RING",
                fontSize = 9.sp,
                color = Color(0xFFFFB74D),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "65",
                    fontSize = 28.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "kWh",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "80%",
                    fontSize = 14.sp,
                    color = Color(0xFFFFB74D),
                    fontWeight = FontWeight.Medium
                )
            }
            Text(
                text = "BU AY KALAN",
                fontSize = 9.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Inner ring label
            Text(
                text = "INNER RING",
                fontSize = 8.sp,
                color = Color(0xFFFF4081),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "-17",
                    fontSize = 22.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "kWh",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "40%",
                    fontSize = 12.sp,
                    color = Color(0xFFFF4081),
                    fontWeight = FontWeight.Medium
                )
            }
            Text(
                text = "HAFTA LİMİTİ AŞILDI",
                fontSize = 8.sp,
                color = Color.White.copy(alpha = 0.6f)
            )
        }

        // Car placeholder (centered at bottom of rings)
        Box(
            modifier = Modifier
                .offset(y = 140.dp)
                .size(120.dp, 60.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF4A5568),
                            Color(0xFF2D3748)
                        )
                    ),
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.DirectionsCar,
                contentDescription = "Car",
                tint = Color(0xFF90A4AE),
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Composable
fun SmartAssistantCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E2936).copy(alpha = 0.8f)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        Color(0xFF2A3744),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.GraphicEq,
                    contentDescription = "Assistant",
                    tint = Color(0xFF4DD0E1),
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Text content
            Column {
                Text(
                    text = "AKILLI ASİSTAN",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Haftalık limit aşıldı. Ev dışı şarj öneriliyor. Tahmini ek maliyet: ₺120.",
                    fontSize = 14.sp,
                    color = Color.White,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    NavigationBar(
        containerColor = Color(0xFF0F1C2E).copy(alpha = 0.95f),
        contentColor = Color.White,
        modifier = Modifier.height(80.dp)
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Ana Sayfa",
                    modifier = Modifier.size(24.dp)
                )
            },
            label = { Text("Ana Sayfa", fontSize = 11.sp) },
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4DD0E1),
                selectedTextColor = Color(0xFF4DD0E1),
                unselectedIconColor = Color.White.copy(alpha = 0.6f),
                unselectedTextColor = Color.White.copy(alpha = 0.6f),
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.BarChart,
                    contentDescription = "İstatistikler",
                    modifier = Modifier.size(24.dp)
                )
            },
            label = { Text("İstatistikler", fontSize = 11.sp) },
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4DD0E1),
                selectedTextColor = Color(0xFF4DD0E1),
                unselectedIconColor = Color.White.copy(alpha = 0.6f),
                unselectedTextColor = Color.White.copy(alpha = 0.6f),
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Ayarlar",
                    modifier = Modifier.size(24.dp)
                )
            },
            label = { Text("Ayarlar", fontSize = 11.sp) },
            selected = selectedTab == 2,
            onClick = { onTabSelected(2) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF4DD0E1),
                selectedTextColor = Color(0xFF4DD0E1),
                unselectedIconColor = Color.White.copy(alpha = 0.6f),
                unselectedTextColor = Color.White.copy(alpha = 0.6f),
                indicatorColor = Color.Transparent
            )
        )
    }
}
