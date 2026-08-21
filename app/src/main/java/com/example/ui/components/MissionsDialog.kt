package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
import com.example.model.GameStats
import com.example.model.MissionType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class MissionFilterTab {
    ALL,
    DAILY,
    WEEKLY,
    STARTER
}

@Composable
fun MissionsDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    gameStats: GameStats,
    claimedMissions: Set<String>,
    onClaimMission: (ArrowMission) -> Unit
) {
    if (!showDialog) return

    var selectedTab by remember { mutableStateOf(MissionFilterTab.ALL) }
    val listState = rememberLazyListState()

    // Always scroll to the very top (index 0) whenever tab changes or dialog is shown
    LaunchedEffect(selectedTab, showDialog) {
        listState.scrollToItem(0)
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding(),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 4.dp)
            ) {
                // 1. Top Header Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null,
                                tint = Color(0xFF888888),
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Daily & Weekly missions rotate automatically",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF777777)
                            )
                        }
                    }

                    // Close Button
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(38.dp)
                            .background(Color(0xFFF0F0F4), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color(0xFF111111),
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                // Stats summary bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF7F7FA))
                        .border(1.dp, Color(0xFFE2E2E8), RoundedCornerShape(12.dp))
                        .padding(horizontal = 8.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    MissionStatItem(label = "Total Hits", value = "${gameStats.totalHits}")
                    MissionStatItem(label = "Level", value = "Lv.${gameStats.currentLevel}")
                    MissionStatItem(label = "Today Hits", value = "${gameStats.dailyHits}")
                    MissionStatItem(label = "Week Hits", value = "${gameStats.weeklyHits}")
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Filter Tabs (ALL, DAILY, WEEKLY, MILESTONES)
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val tabs = listOf(
                        MissionFilterTab.ALL to "ALL (${ArrowMissionCatalog.allMissions.size})",
                        MissionFilterTab.DAILY to "DAILY (20)",
                        MissionFilterTab.WEEKLY to "WEEKLY (20)",
                        MissionFilterTab.STARTER to "STARTER (10)"
                    )
                    items(tabs) { (tab, title) ->
                        val isSelected = selectedTab == tab
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) Color(0xFF111111) else Color(0xFFF0F0F4),
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { selectedTab = tab }
                        ) {
                            Text(
                                text = title,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else Color(0xFF555555),
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Filter & Sort Missions:
                // Completed Unclaimed -> Top, In-Progress -> Middle, Claimed -> Bottom
                val filteredMissions = remember(selectedTab) {
                    when (selectedTab) {
                        MissionFilterTab.ALL -> ArrowMissionCatalog.allMissions
                        MissionFilterTab.DAILY -> ArrowMissionCatalog.allMissions.filter { it.type == MissionType.DAILY }
                        MissionFilterTab.WEEKLY -> ArrowMissionCatalog.allMissions.filter { it.type == MissionType.WEEKLY }
                        MissionFilterTab.STARTER -> ArrowMissionCatalog.allMissions.filter { it.type == MissionType.STARTER }
                    }
                }

                val sortedMissions = remember(filteredMissions, gameStats, claimedMissions) {
                    filteredMissions.sortedWith(
                        compareBy<ArrowMission> { mission ->
                            val currentProgress = mission.checkProgress(gameStats)
                            val isCompleted = currentProgress >= mission.targetValue
                            val isClaimed = claimedMissions.contains(mission.id)
                            when {
                                isCompleted && !isClaimed -> 0 // TOP: Ready to claim
                                !isCompleted && !isClaimed -> 1 // MIDDLE: In progress
                                else -> 2                       // BOTTOM: Already claimed
                            }
                        }.thenByDescending { mission ->
                            val progress = mission.checkProgress(gameStats)
                            if (mission.targetValue > 0) progress.toFloat() / mission.targetValue.toFloat() else 0f
                        }
                    )
                }

                val coroutineScope = rememberCoroutineScope()

                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(top = 4.dp, bottom = 28.dp)
                ) {
                    items(sortedMissions) { mission ->
                        val currentProgress = mission.checkProgress(gameStats)
                        val isCompleted = currentProgress >= mission.targetValue
                        val isClaimed = claimedMissions.contains(mission.id)

                        MissionCardItem(
                            mission = mission,
                            currentProgress = currentProgress,
                            isCompleted = isCompleted,
                            isClaimed = isClaimed,
                            onClaimClick = {
                                val savedIndex = listState.firstVisibleItemIndex
                                val savedOffset = listState.firstVisibleItemScrollOffset
                                onClaimMission(mission)
                                coroutineScope.launch {
                                    listState.scrollToItem(savedIndex, savedOffset)
                                }
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
            fontSize = 13.sp,
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
    var isClaiming by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val claimScale by animateFloatAsState(
        targetValue = if (isClaiming) 1.15f else 1f,
        animationSpec = tween(durationMillis = 250),
        label = "claim_button_scale"
    )

    val borderStroke = when {
        isClaimed -> BorderStroke(1.dp, Color(0xFFE8E8EE))
        isClaiming -> BorderStroke(2.dp, Color(0xFFFFD700))
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
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                // Top line: Mission Title & Category Tag & Rewards
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        // Category Pill Tag
                        val (tagText, tagBg, tagColor) = when (mission.type) {
                            MissionType.STARTER -> Triple("STARTER", Color(0xFFE8EAF6), Color(0xFF3F51B5))
                            MissionType.DAILY -> Triple("DAILY", Color(0xFFFFF3E0), Color(0xFFE65100))
                            MissionType.WEEKLY -> Triple("WEEKLY", Color(0xFFEDE7F6), Color(0xFF673AB7))
                        }
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = tagBg,
                            modifier = Modifier.padding(end = 6.dp)
                        ) {
                            Text(
                                text = tagText,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Black,
                                color = tagColor,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                            )
                        }

                        Text(
                            text = mission.title,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            color = if (isClaimed) Color(0xFF888888) else Color(0xFF111111)
                        )
                    }

                    // Reward badge (Pure XP only - No coins in missions)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                if (isClaimed) Color(0xFFEEEEF2) else Color(0xFFFFF9E6)
                            )
                            .border(
                                1.dp,
                                if (isClaimed) Color.Transparent else Color(0xFFD4AF37),
                                RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "+${mission.xpReward} XP",
                            fontSize = 9.sp,
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

                Spacer(modifier = Modifier.height(8.dp))

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

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "$currentProgress / ${mission.targetValue}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isClaimed) Color(0xFF888888) else Color(0xFF111111),
                        textAlign = TextAlign.End,
                        modifier = Modifier.widthIn(min = 40.dp)
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
                            modifier = Modifier.width(76.dp)
                        ) {
                            Text(
                                text = "CLAIMED",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF888888),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 7.dp)
                            )
                        }
                    }

                    isCompleted -> {
                        Button(
                            onClick = {
                                if (!isClaiming && !isClaimed) {
                                    coroutineScope.launch {
                                        isClaiming = true
                                        delay(500) // 0.5s interactive claiming animation
                                        onClaimClick()
                                        isClaiming = false
                                    }
                                }
                            },
                            enabled = !isClaiming,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isClaiming) Color(0xFFFFD700) else Color(0xFFD4AF37),
                                contentColor = Color.Black
                            ),
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier
                                .width(76.dp)
                                .height(32.dp)
                                .scale(claimScale)
                        ) {
                            if (isClaiming) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Claimed",
                                        tint = Color.Black,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text(
                                        text = "+XP",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                            } else {
                                Text(
                                    text = "CLAIM",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    else -> {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFFAFAFC),
                            border = BorderStroke(1.dp, Color(0xFFE2E2E8)),
                            modifier = Modifier.width(76.dp)
                        ) {
                            Text(
                                text = "PROGRESS",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF888888),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 7.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
