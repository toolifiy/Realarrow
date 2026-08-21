package com.example.model

enum class MissionType {
    STARTER, // Permanent milestone - first hits, first games, never resets
    DAILY,   // Daily challenge - auto refreshes every day at midnight
    WEEKLY   // Weekly challenge - auto refreshes every week
}

data class GameStats(
    val totalHits: Int,
    val bestTimeMs: Long,
    val skinsSize: Int,
    val dotsSize: Int,
    val coins: Int,
    val coinsSpent: Int,
    val gamesPlayed: Int,
    val currentLevel: Int,
    val dailyHits: Int,
    val dailyGames: Int,
    val dailyBestTimeMs: Long,
    val dailyCoinsEarned: Int,
    val weeklyHits: Int,
    val weeklyGames: Int,
    val weeklyBestTimeMs: Long,
    val weeklyCoinsEarned: Int
)

data class ArrowMission(
    val id: String,
    val title: String,
    val desc: String,
    val xpReward: Int,
    val coinReward: Int = 0,
    val targetValue: Int,
    val type: MissionType,
    val checkProgress: (GameStats) -> Int
)

object ArrowMissionCatalog {
    val allMissions: List<ArrowMission> = buildList {

        // ==========================================
        // 1. PERMANENT / STARTER MILESTONES (10 MISSIONS) - NEVER RESETS
        // ==========================================
        add(
            ArrowMission(
                id = "perm_first_hit",
                title = "First Spark",
                desc = "Hit your very first arrow target.",
                xpReward = 30,
                coinReward = 10,
                targetValue = 1,
                type = MissionType.STARTER,
                checkProgress = { it.totalHits.coerceAtMost(1) }
            )
        )
        add(
            ArrowMission(
                id = "perm_five_hits",
                title = "Quick Reflex",
                desc = "Complete your first 5 arrow hits.",
                xpReward = 40,
                coinReward = 15,
                targetValue = 5,
                type = MissionType.STARTER,
                checkProgress = { it.totalHits.coerceAtMost(5) }
            )
        )
        add(
            ArrowMission(
                id = "perm_fifteen_hits",
                title = "Target Apprentice",
                desc = "Accumulate 15 total arrow hits.",
                xpReward = 50,
                coinReward = 20,
                targetValue = 15,
                type = MissionType.STARTER,
                checkProgress = { it.totalHits.coerceAtMost(15) }
            )
        )
        add(
            ArrowMission(
                id = "perm_fifty_hits",
                title = "Sharpshooter Scout",
                desc = "Reach 50 total lifetime target hits.",
                xpReward = 70,
                coinReward = 35,
                targetValue = 50,
                type = MissionType.STARTER,
                checkProgress = { it.totalHits.coerceAtMost(50) }
            )
        )
        add(
            ArrowMission(
                id = "perm_first_game",
                title = "Arcade Initiate",
                desc = "Start and play your first game session.",
                xpReward = 30,
                coinReward = 10,
                targetValue = 1,
                type = MissionType.STARTER,
                checkProgress = { it.gamesPlayed.coerceAtMost(1) }
            )
        )
        add(
            ArrowMission(
                id = "perm_speed_500",
                title = "Sub-500ms Reflex",
                desc = "Achieve a reaction speed under 500ms.",
                xpReward = 60,
                coinReward = 25,
                targetValue = 1,
                type = MissionType.STARTER,
                checkProgress = { if (it.bestTimeMs in 1..500) 1 else 0 }
            )
        )
        add(
            ArrowMission(
                id = "perm_speed_350",
                title = "Swift Pace",
                desc = "Achieve a reaction speed under 350ms.",
                xpReward = 80,
                coinReward = 40,
                targetValue = 1,
                type = MissionType.STARTER,
                checkProgress = { if (it.bestTimeMs in 1..350) 1 else 0 }
            )
        )
        add(
            ArrowMission(
                id = "perm_first_skin",
                title = "Custom Style",
                desc = "Unlock at least 2 Arrow Skins in the shop.",
                xpReward = 50,
                coinReward = 30,
                targetValue = 2,
                type = MissionType.STARTER,
                checkProgress = { it.skinsSize.coerceAtMost(2) }
            )
        )
        add(
            ArrowMission(
                id = "perm_first_dot",
                title = "Dot Collector",
                desc = "Unlock at least 2 Dot Skins in the shop.",
                xpReward = 50,
                coinReward = 30,
                targetValue = 2,
                type = MissionType.STARTER,
                checkProgress = { it.dotsSize.coerceAtMost(2) }
            )
        )
        add(
            ArrowMission(
                id = "perm_level_two",
                title = "Level 2 Ascendant",
                desc = "Reach Player Level 2 by earning XP.",
                xpReward = 90,
                coinReward = 50,
                targetValue = 2,
                type = MissionType.STARTER,
                checkProgress = { it.currentLevel.coerceAtMost(2) }
            )
        )

        // ==========================================
        // 2. DAILY MISSIONS (20 MISSIONS) - AUTO RESETS DAILY AT MIDNIGHT
        // ==========================================
        add(
            ArrowMission(
                id = "daily_hits_10",
                title = "Morning Warmup",
                desc = "Hit 10 arrow targets today.",
                xpReward = 35,
                coinReward = 15,
                targetValue = 10,
                type = MissionType.DAILY,
                checkProgress = { it.dailyHits.coerceAtMost(10) }
            )
        )
        add(
            ArrowMission(
                id = "daily_hits_25",
                title = "Daily Target Grinder",
                desc = "Hit 25 arrow targets today.",
                xpReward = 50,
                coinReward = 25,
                targetValue = 25,
                type = MissionType.DAILY,
                checkProgress = { it.dailyHits.coerceAtMost(25) }
            )
        )
        add(
            ArrowMission(
                id = "daily_hits_50",
                title = "Focus Marathon",
                desc = "Reach 50 arrow hits today.",
                xpReward = 75,
                coinReward = 40,
                targetValue = 50,
                type = MissionType.DAILY,
                checkProgress = { it.dailyHits.coerceAtMost(50) }
            )
        )
        add(
            ArrowMission(
                id = "daily_hits_75",
                title = "Daily Centurion",
                desc = "Reach 75 arrow hits today.",
                xpReward = 95,
                coinReward = 60,
                targetValue = 75,
                type = MissionType.DAILY,
                checkProgress = { it.dailyHits.coerceAtMost(75) }
            )
        )
        add(
            ArrowMission(
                id = "daily_hits_100",
                title = "Daily Master Archer",
                desc = "Hit 100 arrow targets in one day.",
                xpReward = 120,
                coinReward = 80,
                targetValue = 100,
                type = MissionType.DAILY,
                checkProgress = { it.dailyHits.coerceAtMost(100) }
            )
        )
        add(
            ArrowMission(
                id = "daily_games_2",
                title = "Daily Challenger",
                desc = "Play at least 2 game sessions today.",
                xpReward = 30,
                coinReward = 10,
                targetValue = 2,
                type = MissionType.DAILY,
                checkProgress = { it.dailyGames.coerceAtMost(2) }
            )
        )
        add(
            ArrowMission(
                id = "daily_games_5",
                title = "Arena Explorer",
                desc = "Play at least 5 game sessions today.",
                xpReward = 55,
                coinReward = 25,
                targetValue = 5,
                type = MissionType.DAILY,
                checkProgress = { it.dailyGames.coerceAtMost(5) }
            )
        )
        add(
            ArrowMission(
                id = "daily_games_8",
                title = "Dedicated Tapper",
                desc = "Play at least 8 game sessions today.",
                xpReward = 80,
                coinReward = 45,
                targetValue = 8,
                type = MissionType.DAILY,
                checkProgress = { it.dailyGames.coerceAtMost(8) }
            )
        )
        add(
            ArrowMission(
                id = "daily_games_12",
                title = "Unstoppable Drive",
                desc = "Play 12 game sessions today.",
                xpReward = 110,
                coinReward = 70,
                targetValue = 12,
                type = MissionType.DAILY,
                checkProgress = { it.dailyGames.coerceAtMost(12) }
            )
        )
        add(
            ArrowMission(
                id = "daily_speed_450",
                title = "Daily Swift Reflex",
                desc = "Hit a reaction speed under 450ms today.",
                xpReward = 45,
                coinReward = 20,
                targetValue = 1,
                type = MissionType.DAILY,
                checkProgress = { if (it.dailyBestTimeMs in 1..450) 1 else 0 }
            )
        )
        add(
            ArrowMission(
                id = "daily_speed_380",
                title = "Daily Speed Demon",
                desc = "Hit a reaction speed under 380ms today.",
                xpReward = 65,
                coinReward = 35,
                targetValue = 1,
                type = MissionType.DAILY,
                checkProgress = { if (it.dailyBestTimeMs in 1..380) 1 else 0 }
            )
        )
        add(
            ArrowMission(
                id = "daily_speed_300",
                title = "Daily Flash Strike",
                desc = "Hit a reaction speed under 300ms today.",
                xpReward = 90,
                coinReward = 50,
                targetValue = 1,
                type = MissionType.DAILY,
                checkProgress = { if (it.dailyBestTimeMs in 1..300) 1 else 0 }
            )
        )
        add(
            ArrowMission(
                id = "daily_speed_250",
                title = "Daily Supersonic",
                desc = "Hit a reaction speed under 250ms today.",
                xpReward = 130,
                coinReward = 90,
                targetValue = 1,
                type = MissionType.DAILY,
                checkProgress = { if (it.dailyBestTimeMs in 1..250) 1 else 0 }
            )
        )
        add(
            ArrowMission(
                id = "daily_coins_15",
                title = "Coin Harvester",
                desc = "Earn 15 coins today from target hits.",
                xpReward = 40,
                coinReward = 15,
                targetValue = 15,
                type = MissionType.DAILY,
                checkProgress = { it.dailyCoinsEarned.coerceAtMost(15) }
            )
        )
        add(
            ArrowMission(
                id = "daily_coins_35",
                title = "Bounty Collector",
                desc = "Earn 35 coins today from target hits.",
                xpReward = 60,
                coinReward = 30,
                targetValue = 35,
                type = MissionType.DAILY,
                checkProgress = { it.dailyCoinsEarned.coerceAtMost(35) }
            )
        )
        add(
            ArrowMission(
                id = "daily_coins_60",
                title = "Daily Vault Builder",
                desc = "Earn 60 coins today from target hits.",
                xpReward = 85,
                coinReward = 50,
                targetValue = 60,
                type = MissionType.DAILY,
                checkProgress = { it.dailyCoinsEarned.coerceAtMost(60) }
            )
        )
        add(
            ArrowMission(
                id = "daily_coins_100",
                title = "Treasure Hunter",
                desc = "Earn 100 coins today from target hits.",
                xpReward = 125,
                coinReward = 85,
                targetValue = 100,
                type = MissionType.DAILY,
                checkProgress = { it.dailyCoinsEarned.coerceAtMost(100) }
            )
        )
        add(
            ArrowMission(
                id = "daily_hits_125",
                title = "Iron Will",
                desc = "Complete 125 target hits today.",
                xpReward = 140,
                coinReward = 100,
                targetValue = 125,
                type = MissionType.DAILY,
                checkProgress = { it.dailyHits.coerceAtMost(125) }
            )
        )
        add(
            ArrowMission(
                id = "daily_games_15",
                title = "Apex Gladiator",
                desc = "Play 15 game rounds today.",
                xpReward = 135,
                coinReward = 95,
                targetValue = 15,
                type = MissionType.DAILY,
                checkProgress = { it.dailyGames.coerceAtMost(15) }
            )
        )
        add(
            ArrowMission(
                id = "daily_speed_220",
                title = "Thunderbolt Tap",
                desc = "Hit a reaction speed under 220ms today.",
                xpReward = 160,
                coinReward = 120,
                targetValue = 1,
                type = MissionType.DAILY,
                checkProgress = { if (it.dailyBestTimeMs in 1..220) 1 else 0 }
            )
        )

        // ==========================================
        // 3. WEEKLY MISSIONS (20 MISSIONS) - AUTO RESETS WEEKLY
        // ==========================================
        add(
            ArrowMission(
                id = "weekly_hits_150",
                title = "Century Scout",
                desc = "Achieve 150 arrow hits this week.",
                xpReward = 120,
                coinReward = 75,
                targetValue = 150,
                type = MissionType.WEEKLY,
                checkProgress = { it.weeklyHits.coerceAtMost(150) }
            )
        )
        add(
            ArrowMission(
                id = "weekly_hits_300",
                title = "Weekly Sharpshooter",
                desc = "Achieve 300 arrow hits this week.",
                xpReward = 180,
                coinReward = 120,
                targetValue = 300,
                type = MissionType.WEEKLY,
                checkProgress = { it.weeklyHits.coerceAtMost(300) }
            )
        )
        add(
            ArrowMission(
                id = "weekly_hits_500",
                title = "Grand Hunter",
                desc = "Achieve 500 arrow hits this week.",
                xpReward = 250,
                coinReward = 180,
                targetValue = 500,
                type = MissionType.WEEKLY,
                checkProgress = { it.weeklyHits.coerceAtMost(500) }
            )
        )
        add(
            ArrowMission(
                id = "weekly_hits_800",
                title = "Millennium Striker",
                desc = "Achieve 800 arrow hits this week.",
                xpReward = 350,
                coinReward = 260,
                targetValue = 800,
                type = MissionType.WEEKLY,
                checkProgress = { it.weeklyHits.coerceAtMost(800) }
            )
        )
        add(
            ArrowMission(
                id = "weekly_hits_1200",
                title = "Legendary Archer",
                desc = "Achieve 1200 arrow hits this week.",
                xpReward = 450,
                coinReward = 350,
                targetValue = 1200,
                type = MissionType.WEEKLY,
                checkProgress = { it.weeklyHits.coerceAtMost(1200) }
            )
        )
        add(
            ArrowMission(
                id = "weekly_games_15",
                title = "Weekly Regular",
                desc = "Play 15 game rounds this week.",
                xpReward = 100,
                coinReward = 60,
                targetValue = 15,
                type = MissionType.WEEKLY,
                checkProgress = { it.weeklyGames.coerceAtMost(15) }
            )
        )
        add(
            ArrowMission(
                id = "weekly_games_30",
                title = "Arena Veteran",
                desc = "Play 30 game rounds this week.",
                xpReward = 160,
                coinReward = 110,
                targetValue = 30,
                type = MissionType.WEEKLY,
                checkProgress = { it.weeklyGames.coerceAtMost(30) }
            )
        )
        add(
            ArrowMission(
                id = "weekly_games_50",
                title = "Marathon Battler",
                desc = "Play 50 game rounds this week.",
                xpReward = 240,
                coinReward = 175,
                targetValue = 50,
                type = MissionType.WEEKLY,
                checkProgress = { it.weeklyGames.coerceAtMost(50) }
            )
        )
        add(
            ArrowMission(
                id = "weekly_games_80",
                title = "Endless Warrior",
                desc = "Play 80 game rounds this week.",
                xpReward = 320,
                coinReward = 250,
                targetValue = 80,
                type = MissionType.WEEKLY,
                checkProgress = { it.weeklyGames.coerceAtMost(80) }
            )
        )
        add(
            ArrowMission(
                id = "weekly_coins_150",
                title = "Weekly Silver Stash",
                desc = "Earn 150 coins this week.",
                xpReward = 110,
                coinReward = 70,
                targetValue = 150,
                type = MissionType.WEEKLY,
                checkProgress = { it.weeklyCoinsEarned.coerceAtMost(150) }
            )
        )
        add(
            ArrowMission(
                id = "weekly_coins_300",
                title = "Gold Merchant",
                desc = "Earn 300 coins this week.",
                xpReward = 170,
                coinReward = 120,
                targetValue = 300,
                type = MissionType.WEEKLY,
                checkProgress = { it.weeklyCoinsEarned.coerceAtMost(300) }
            )
        )
        add(
            ArrowMission(
                id = "weekly_coins_600",
                title = "Royal Treasury",
                desc = "Earn 600 coins this week.",
                xpReward = 260,
                coinReward = 190,
                targetValue = 600,
                type = MissionType.WEEKLY,
                checkProgress = { it.weeklyCoinsEarned.coerceAtMost(600) }
            )
        )
        add(
            ArrowMission(
                id = "weekly_coins_1000",
                title = "Dragon's Vault",
                desc = "Earn 1,000 coins this week.",
                xpReward = 380,
                coinReward = 300,
                targetValue = 1000,
                type = MissionType.WEEKLY,
                checkProgress = { it.weeklyCoinsEarned.coerceAtMost(1000) }
            )
        )
        add(
            ArrowMission(
                id = "weekly_speed_280",
                title = "Weekly Sonic Pulse",
                desc = "Hit a reaction speed under 280ms this week.",
                xpReward = 140,
                coinReward = 90,
                targetValue = 1,
                type = MissionType.WEEKLY,
                checkProgress = { if (it.weeklyBestTimeMs in 1..280) 1 else 0 }
            )
        )
        add(
            ArrowMission(
                id = "weekly_speed_240",
                title = "Weekly Hyper Reflex",
                desc = "Hit a reaction speed under 240ms this week.",
                xpReward = 200,
                coinReward = 140,
                targetValue = 1,
                type = MissionType.WEEKLY,
                checkProgress = { if (it.weeklyBestTimeMs in 1..240) 1 else 0 }
            )
        )
        add(
            ArrowMission(
                id = "weekly_speed_200",
                title = "Weekly Lightning Master",
                desc = "Hit a reaction speed under 200ms this week.",
                xpReward = 300,
                coinReward = 220,
                targetValue = 1,
                type = MissionType.WEEKLY,
                checkProgress = { if (it.weeklyBestTimeMs in 1..200) 1 else 0 }
            )
        )
        add(
            ArrowMission(
                id = "weekly_skins_5",
                title = "Quiver Connoisseur",
                desc = "Own at least 5 Arrow Skins.",
                xpReward = 150,
                coinReward = 100,
                targetValue = 5,
                type = MissionType.WEEKLY,
                checkProgress = { it.skinsSize.coerceAtMost(5) }
            )
        )
        add(
            ArrowMission(
                id = "weekly_dots_5",
                title = "Master of Sights",
                desc = "Own at least 5 Dot Skins.",
                xpReward = 150,
                coinReward = 100,
                targetValue = 5,
                type = MissionType.WEEKLY,
                checkProgress = { it.dotsSize.coerceAtMost(5) }
            )
        )
        add(
            ArrowMission(
                id = "weekly_level_5",
                title = "Precision Ascendant",
                desc = "Reach Player Level 5 or higher.",
                xpReward = 220,
                coinReward = 150,
                targetValue = 5,
                type = MissionType.WEEKLY,
                checkProgress = { it.currentLevel.coerceAtMost(5) }
            )
        )
        add(
            ArrowMission(
                id = "weekly_level_10",
                title = "Supreme Ascendant",
                desc = "Reach Player Level 10 or higher.",
                xpReward = 400,
                coinReward = 300,
                targetValue = 10,
                type = MissionType.WEEKLY,
                checkProgress = { it.currentLevel.coerceAtMost(10) }
            )
        )
    }
}
