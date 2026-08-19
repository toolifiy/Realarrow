package com.example.model

data class ArrowMission(
    val id: String,
    val title: String,
    val desc: String,
    val xpReward: Int,
    val targetValue: Int,
    val checkProgress: (totalHits: Int, bestTimeMs: Long, skinsSize: Int, dotsSize: Int, coins: Int, gamesPlayed: Int, currentLevel: Int) -> Int
)

object ArrowMissionCatalog {
    val allMissions: List<ArrowMission> = buildList {
        // --- 1. TOTAL TARGET HITS MISSIONS (Missions 1 to 30) ---
        val hitTargets = listOf(
            1, 3, 5, 8, 12, 18, 25, 35, 50, 70, 95, 125, 160, 200, 250, 310, 380, 460, 550, 650, 775, 900, 1050, 1250, 1500, 1800, 2200, 2700, 3300, 4000
        )
        val hitTitles = listOf(
            "First Spark", "Warmup Tap", "Reflex Rookie", "Quick Hands", "Apprentice Archer",
            "Sharpshooter", "Speed Scout", "Target Seeker", "Eagle Eye", "Centurion Tapper",
            "Focus Master", "Laser Precision", "Arrow Virtuoso", "Kinetic Striker", "Swiftblade",
            "Hyper Reflex", "Thunderbolt Tap", "Apex Hunter", "Legend of Arrows", "Millennium Master",
            "Grand Centurion", "Supreme Striker", "Celestial Archer", "Phantom Reflex", "Immortal Hunter",
            "Infinite Marksman", "Cosmic Stalker", "Omni Striker", "Transcendental Ace", "God of Precision"
        )
        for (i in hitTargets.indices) {
            val target = hitTargets[i]
            // Maximum XP per mission is strictly capped at 200 XP
            val xp = (25 + i * 6).coerceAtMost(200)
            add(
                ArrowMission(
                    id = "mission_hits_${i + 1}",
                    title = hitTitles.getOrElse(i) { "Target Hit Tier ${i + 1}" },
                    desc = "Reach $target total arrow hits across all games.",
                    xpReward = xp,
                    targetValue = target,
                    checkProgress = { totalHits, _, _, _, _, _, _ -> totalHits.coerceAtMost(target) }
                )
            )
        }

        // --- 2. REACTION TIME MILESTONES (Missions 31 to 46) ---
        val speedMilestones = listOf(
            650 to "Sub-650ms Pace",
            600 to "Under 600ms Club",
            550 to "Sub-550ms Reflex",
            500 to "Under 500ms Club",
            450 to "Sub-450ms Precision",
            400 to "Under 400ms Club",
            380 to "Swift Velocity",
            350 to "Under 350ms Club",
            320 to "Sonic Pulse",
            300 to "Under 300ms Club",
            280 to "Supersonic Tap",
            260 to "Hyper Reflex Club",
            240 to "Under 240ms Legend",
            220 to "Flash Lightning",
            200 to "Under 200ms Master",
            180 to "Godlike Reaction"
        )
        for (i in speedMilestones.indices) {
            val (ms, title) = speedMilestones[i]
            val xp = (40 + i * 10).coerceAtMost(200)
            add(
                ArrowMission(
                    id = "mission_speed_${i + 1}",
                    title = title,
                    desc = "Achieve a best reaction speed of ${ms}ms or faster.",
                    xpReward = xp,
                    targetValue = 1,
                    checkProgress = { _, bestTimeMs, _, _, _, _, _ -> if (bestTimeMs in 1..ms) 1 else 0 }
                )
            )
        }

        // --- 3. GAMES PLAYED & EXPERIENCE (Missions 47 to 66) ---
        val gamesTargets = listOf(
            1, 2, 4, 6, 9, 12, 16, 20, 26, 32, 40, 50, 65, 80, 100, 125, 155, 190, 235, 300
        )
        val gamesTitles = listOf(
            "Game On", "Warm Up", "Regular Challenger", "Arcade Explorer", "Dedicated Player",
            "Reflex Enthusiast", "Arena Battler", "Marathon Runner", "Unstoppable Drive", "True Veteran",
            "Centurion Matches", "Tournament Veteran", "Arcade Legend", "Iron Will", "Endless Warrior",
            "Reflex Champion", "Relentless Fighter", "Master Combatant", "Grand Arena Lord", "Eternal Challenger"
        )
        for (i in gamesTargets.indices) {
            val target = gamesTargets[i]
            val xp = (30 + i * 8).coerceAtMost(200)
            add(
                ArrowMission(
                    id = "mission_games_${i + 1}",
                    title = gamesTitles.getOrElse(i) { "Games Master ${i + 1}" },
                    desc = "Start and play at least $target game rounds.",
                    xpReward = xp,
                    targetValue = target,
                    checkProgress = { _, _, _, _, _, gamesPlayed, _ -> gamesPlayed.coerceAtMost(target) }
                )
            )
        }

        // --- 4. COIN VAULT & WEALTH (Missions 67 to 86) ---
        val coinTargets = listOf(
            10, 25, 45, 75, 120, 180, 250, 350, 480, 650, 850, 1100, 1400, 1800, 2300, 3000, 4000, 5500, 7500, 10000
        )
        val coinTitles = listOf(
            "Pocket Change", "Piggy Bank", "Coin Hoarder", "Bronze Vault", "Silver Stash",
            "Gold Merchant", "Treasure Finder", "Banker's Pride", "Golden Reserves", "Royal Treasury",
            "Millionaire Mindset", "Midas Touch", "Dragon's Vault", "Empire Treasury", "Infinite Gold",
            "Fortune Sovereign", "Gilded Emperor", "Cosmic Tycoon", "Celestial Vault", "Wealth Supreme"
        )
        for (i in coinTargets.indices) {
            val target = coinTargets[i]
            val xp = (35 + i * 8).coerceAtMost(200)
            add(
                ArrowMission(
                    id = "mission_coins_${i + 1}",
                    title = coinTitles.getOrElse(i) { "Coin Collector ${i + 1}" },
                    desc = "Accumulate and hold at least $target coins.",
                    xpReward = xp,
                    targetValue = target,
                    checkProgress = { _, _, _, _, coins, _, _ -> coins.coerceAtMost(target) }
                )
            )
        }

        // --- 5. ARROW & DOT SKINS COLLECTOR (Missions 87 to 102) ---
        val skinTargets = listOf(
            1 to "First Arrow Style",
            2 to "Dual Arrow Skins",
            3 to "Triple Threat Skins",
            4 to "Arrow Stylist",
            5 to "Arrow Wardrobe",
            6 to "Arrow Collector",
            7 to "Arrow Master",
            8 to "Full Arrow Arsenal",
            10 to "Epic Quiver",
            12 to "Legendary Quiver",
            15 to "Mythic Arrow King"
        )
        for (i in skinTargets.indices) {
            val (target, title) = skinTargets[i]
            val xp = (50 + i * 14).coerceAtMost(200)
            add(
                ArrowMission(
                    id = "mission_arrow_skins_${i + 1}",
                    title = title,
                    desc = "Unlock at least $target Arrow Skins in the shop.",
                    xpReward = xp,
                    targetValue = target,
                    checkProgress = { _, _, skinsSize, _, _, _, _ -> skinsSize.coerceAtMost(target) }
                )
            )
        }

        val dotTargets = listOf(
            1 to "First Dot Spark",
            2 to "Dot Collector",
            3 to "Dot Enthusiast",
            4 to "Dot Specialist",
            5 to "Dot Overlord",
            6 to "Cosmic Dot Vault",
            7 to "Celestial Dots",
            8 to "Master of Dots"
        )
        for (i in dotTargets.indices) {
            val (target, title) = dotTargets[i]
            val xp = (50 + i * 15).coerceAtMost(200)
            add(
                ArrowMission(
                    id = "mission_dot_skins_${i + 1}",
                    title = title,
                    desc = "Unlock at least $target Dot Skins in the shop.",
                    xpReward = xp,
                    targetValue = target,
                    checkProgress = { _, _, _, dotsSize, _, _, _ -> dotsSize.coerceAtMost(target) }
                )
            )
        }

        // --- 6. LEVEL & RANK MILESTONES (Missions 103 to 120) ---
        val levelMilestones = listOf(
            1 to "Level 1: Novice Ascendant",
            2 to "Level 2: Swift Challenger",
            3 to "Level 3: Nimble Scout",
            4 to "Level 4: Reflex Adept",
            5 to "Level 5: Precision Rank",
            6 to "Level 6: Vector Knight",
            7 to "Level 7: Hyper Specialist",
            8 to "Level 8: Master Tactician",
            9 to "Level 9: Apex Grandmaster",
            10 to "Level 10: Supreme Ascendant",
            12 to "Level 12: Chrono Overlord",
            15 to "Level 15: Celestial Vanguard",
            18 to "Level 18: Mythic Sovereign",
            20 to "Level 20: Eternal Legend",
            25 to "Level 25: Transcendental Master",
            30 to "Level 30: Omni Sovereign",
            40 to "Level 40: Cosmos Ascendant",
            50 to "Level 50: Infinite Deity"
        )
        for (i in levelMilestones.indices) {
            val (lvl, title) = levelMilestones[i]
            val xp = (60 + i * 8).coerceAtMost(200)
            add(
                ArrowMission(
                    id = "mission_level_${i + 1}",
                    title = title,
                    desc = "Reach Player Level $lvl by earning XP.",
                    xpReward = xp,
                    targetValue = lvl,
                    checkProgress = { _, _, _, _, _, _, currentLevel -> currentLevel.coerceAtMost(lvl) }
                )
            )
        }
    }
}
