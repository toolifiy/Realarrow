package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.ArrowMission
import com.example.model.ArrowMissionCatalog

@Composable
fun MissionsDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    totalHits: Int,
    bestTimeMs: Long,
    skinsCount: Int,
    dotsCount: Int,
    coins: Int,
    gamesPlayed: Int,
    currentLevel: Int,
    claimedMissions: Set<String>,
    onClaimXp: (String, Int) -> Unit
) {
    if (!showDialog) return

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false // Allow full screen width
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .systemBarsPadding(), // Ensures full padding from navigation bar and status bar
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // 1. Title Header Bar (Clean White, Black text, Gold Star)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFD4AF37),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "ARROW MISSIONS",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.5.sp,
                                color = Color(0xFF111111)
                            )
                        }
                        Text(
                            text = "Level up your reflexes to unlock new milestones!",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF777777),
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }

                    // Close Button (Solid Black 'X')
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(38.dp)
                            .background(Color(0xFFF0F0F4), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color(0xFF111111), // Bold Solid Black
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                // Divider
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFE8E8EE))
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Stats summary inside missions board
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF7F7FA))
                        .border(1.dp, Color(0xFFE2E2E8), RoundedCornerShape(12.dp))
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    MissionStatItem(label = "Total Hits", value = "$totalHits")
                    MissionStatItem(label = "Games", value = "$gamesPlayed")
                    MissionStatItem(label = "Skins Owned", value = "${skinsCount + dotsCount}")
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 2. Scrollable Missions List (Full height, no useless bottom button)
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 12.dp)
                ) {
                    items(ArrowMissionCatalog.allMissions) { mission ->
                        val currentProgress = mission.checkProgress(
                            totalHits,
                            bestTimeMs,
                            skinsCount,
                            dotsCount,
                            coins,
                            gamesPlayed,
                            currentLevel
                        )
                        val isCompleted = currentProgress >= mission.targetValue
                        val isClaimed = claimedMissions.contains(mission.id)

                        MissionCardItem(
                            mission = mission,
                            currentProgress = currentProgress,
                            isCompleted = isCompleted,
                            isClaimed = isClaimed,
                            onClaimClick = {
                                onClaimXp(mission.id, mission.xpReward)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MissionStatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFF111111)
        )
        Text(
            text = label.uppercase(),
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF888888),
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
fun MissionCardItem(
    mission: ArrowMission,
    currentProgress: Int,
    isCompleted: Boolean,
    isClaimed: Boolean,
    onClaimClick: () -> Unit
) {
    // Beautiful white item card, glowing golden border if completed but unclaimed
    val borderStroke = when {
        isClaimed -> BorderStroke(1.dp, Color(0xFFE8E8EE))
        isCompleted -> BorderStroke(1.5.dp, Color(0xFFD4AF37)) // Metallic Gold glow
        else -> BorderStroke(1.dp, Color(0xFFE2E2E8))
    }

    val cardBg = if (isClaimed) Color(0xFFFAFAFC) else Color.White

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        border = borderStroke,
        elevation = CardDefaults.cardElevation(defaultElevation = if (isClaimed) 0.dp else 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                // Top line: Mission Title & XP Reward
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = mission.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = if (isClaimed) Color(0xFF888888) else Color(0xFF111111)
                    )

                    // Reward badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (isClaimed) Color(0xFFEEEEF2) else Color(0xFFFFF9E6)
                            )
                            .border(
                                1.dp,
                                if (isClaimed) Color.Transparent else Color(0xFFD4AF37),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "+${mission.xpReward} XP",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = if (isClaimed) Color(0xFF888888) else Color(0xFFB8860B)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Middle line: Description
                Text(
                    text = mission.desc,
                    fontSize = 11.sp,
                    color = if (isClaimed) Color(0xFF999999) else Color(0xFF555555),
                    fontWeight = FontWeight.Normal,
                    lineHeight = 14.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Bottom line: Horizontal progress bar and progress text
                val fraction = if (mission.targetValue > 0) {
                    (currentProgress.toFloat() / mission.targetValue.toFloat()).coerceIn(0f, 1f)
                } else 0f

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Track
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(Color(0xFFEEEEF2))
                    ) {
                        // Progress fill
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(fraction)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(3.dp))
                                .background(
                                    if (isClaimed) {
                                        androidx.compose.ui.graphics.SolidColor(Color(0xFFBBBBBB))
                                    } else {
                                        Brush.horizontalGradient(
                                            colors = listOf(Color(0xFFD4AF37), Color(0xFFFFD700))
                                        )
                                    }
                                )
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "$currentProgress / ${mission.targetValue}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isClaimed) Color(0xFF888888) else Color(0xFF111111),
                        textAlign = TextAlign.End,
                        modifier = Modifier.widthIn(min = 45.dp)
                    )
                }
            }

            // Right side Action Button
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                when {
                    isClaimed -> {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFEEEEF2),
                            modifier = Modifier.width(80.dp)
                        ) {
                            Text(
                                text = "CLAIMED",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF888888),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }

                    isCompleted -> {
                        Button(
                            onClick = onClaimClick,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFD4AF37), // Bright gold
                                contentColor = Color.Black
                            ),
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier
                                .width(80.dp)
                                .height(32.dp)
                        ) {
                            Text(
                                text = "CLAIM XP",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    else -> {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFFAFAFC),
                            border = BorderStroke(1.dp, Color(0xFFE2E2E8)),
                            modifier = Modifier.width(80.dp)
                        ) {
                            Text(
                                text = "PROGRESS",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF888888),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
