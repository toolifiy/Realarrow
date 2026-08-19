package com.example.model

data class ArrowMission(
    val id: String,
    val title: String,
    val desc: String,
    val xpReward: Int,
    val targetValue: Int,
    val checkProgress: (
        totalHits: Int,
        bestTimeMs: Long,
        skinsSize: Int,
        dotsSize: Int,
        coins: Int,
        coinsSpent: Int,
        gamesPlayed: Int,
        currentLevel: Int
    ) -> Int
)

object ArrowMissionCatalog {
    val allMissions: List<ArrowMission> = buildList {
        // --- 1. TOTAL TARGET HITS MISSIONS (Missions 1 to 25) ---
        val hitTargets = listOf(
            1, 3, 5, 8, 12, 18, 25, 35, 50, 70, 95, 125, 160, 200, 250, 310, 380, 460, 550, 650, 775, 900, 1050, 1250, 1500
        )
        val hitTitles = listOf(
            "First Spark", "Warmup Tap", "Reflex Rookie", "Quick Hands", "Apprentice Archer",
            "Sharpshooter", "Speed Scout", "Target Seeker", "Eagle Eye", "Centurion Tapper",
            "Focus Master", "Laser Precision", "Arrow Virtuoso", "Kinetic Striker", "Swiftblade",
            "Hyper Reflex", "Thunderbolt Tap", "Apex Hunter", "Legend of Arrows", "Millennium Master",
            "Grand Centurion", "Supreme Striker", "Celestial Archer", "Phantom Reflex", "Immortal Hunter"
        )
        for (i in hitTargets.indices) {
            val target = hitTargets[i]
            val xp = (25 + i * 6).coerceAtMost(200)
            add(
                ArrowMission(
                    id = "mission_hits_${i + 1}",
                    title = hitTitles.getOrElse(i) { "Target Hit Tier ${i + 1}" },
                    desc = "Reach $target total arrow hits across all games.",
                    xpReward = xp,
                    targetValue = target,
                    checkProgress = { totalHits, _, _, _, _, _, _, _ -> totalHits.coerceAtMost(target) }
                )
            )
        }

        // --- 2. REACTION TIME MILESTONES (Missions 26 to 40) ---
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
            200 to "Under 200ms Master"
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
                    checkProgress = { _, bestTimeMs, _, _, _, _, _, _ -> if (bestTimeMs in 1..ms) 1 else 0 }
                )
            )
        }

        // --- 3. GAMES PLAYED & EXPERIENCE (Missions 41 to 55) ---
        val gamesTargets = listOf(
            1, 2, 4, 6, 9, 12, 16, 20, 26, 32, 40, 50, 65, 80, 100
        )
        val gamesTitles = listOf(
            "Game On", "Warm Up", "Regular Challenger", "Arcade Explorer", "Dedicated Player",
            "Reflex Enthusiast", "Arena Battler", "Marathon Runner", "Unstoppable Drive", "True Veteran",
            "Centurion Matches", "Tournament Veteran", "Arcade Legend", "Iron Will", "Endless Warrior"
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
                    checkProgress = { _, _, _, _, _, _, gamesPlayed, _ -> gamesPlayed.coerceAtMost(target) }
                )
            )
        }

        // --- 4. COIN VAULT & WEALTH (Missions 56 to 70) ---
        val coinTargets = listOf(
            50, 100, 250, 500, 1000, 2000, 3500, 5000, 7500, 10000, 15000, 20000, 25000, 30000, 50000
        )
        val coinTitles = listOf(
            "Pocket Change", "Piggy Bank", "Coin Hoarder", "Bronze Vault", "Silver Stash",
            "Gold Merchant", "Treasure Finder", "Banker's Pride", "Golden Reserves", "Royal Treasury",
            "Millionaire Mindset", "Midas Touch", "Dragon's Vault", "Empire Treasury", "Infinite Gold"
        )
        for (i in coinTargets.indices) {
            val target = coinTargets[i]
            val xp = (35 + i * 8).coerceAtMost(200)
            add(
                ArrowMission(
                    id = "mission_coins_${i + 1}",
                    title = coinTitles.getOrElse(i) { "Coin Collector ${i + 1}" },
                    desc = "Accumulate and hold at least ${com.example.util.FormatUtils.formatCoins(target)} coins.",
                    xpReward = xp,
                    targetValue = target,
                    checkProgress = { _, _, _, _, coins, _, _, _ -> coins.coerceAtMost(target) }
                )
            )
        }

        // --- 5. SPENDING COINS IN SHOP (Missions 71 to 85) ---
        val spendTargets = listOf(
            15, 50, 100, 200, 400, 700, 1200, 2000, 3000, 5000, 8000, 12000, 18000, 25000, 40000
        )
        val spendTitles = listOf(
            "First Purchase", "Window Shopper", "Smart Buyer", "Bargain Hunter", "Shop Regular",
            "Big Spender", "VIP Customer", "High Roller", "Golden Patron", "Market Tycoon",
            "Treasury Spender", "Grand Investor", "Economic Powerhouse", "Sultan of Spends", "Infinite Philanthropist"
        )
        for (i in spendTargets.indices) {
            val target = spendTargets[i]
            val xp = (45 + i * 9).coerceAtMost(200)
            add(
                ArrowMission(
                    id = "mission_spend_${i + 1}",
                    title = spendTitles.getOrElse(i) { "Spending Tier ${i + 1}" },
                    desc = "Spend at least ${com.example.util.FormatUtils.formatCoins(target)} coins buying skins in the shop.",
                    xpReward = xp,
                    targetValue = target,
                    checkProgress = { _, _, _, _, _, coinsSpent, _, _ -> coinsSpent.coerceAtMost(target) }
                )
            )
        }

        // --- 6. ARROW SKINS BOUGHT & UNLOCKED (Missions 86 to 100) ---
        val arrowSkinTargets = listOf(
            2 to "First Custom Arrow",
            3 to "Dual Skin Collector",
            5 to "Arrow Stylist",
            7 to "Arrow Wardrobe",
            10 to "Quiver Specialist",
            14 to "Arrow Connoisseur",
            18 to "Arsenal Builder",
            22 to "Epic Quiver Master",
            28 to "Legendary Fletcher",
            35 to "Mythic Arrow King",
            45 to "Celestial Arsenal",
            55 to "Grandmaster of Bows",
            70 to "Omni Vector Lord",
            85 to "Sovereign Fletcher",
            100 to "Complete Arrow Pantheon"
        )
        for (i in arrowSkinTargets.indices) {
            val (target, title) = arrowSkinTargets[i]
            val xp = (50 + i * 10).coerceAtMost(200)
            add(
                ArrowMission(
                    id = "mission_arrow_skins_${i + 1}",
                    title = title,
                    desc = "Buy and unlock at least $target Arrow Skins in the shop.",
                    xpReward = xp,
                    targetValue = target,
                    checkProgress = { _, _, skinsSize, _, _, _, _, _ -> skinsSize.coerceAtMost(target) }
                )
            )
        }

        // --- 7. DOT SKINS BOUGHT & UNLOCKED (Missions 101 to 115) ---
        val dotSkinTargets = listOf(
            2 to "First Custom Dot",
            3 to "Dual Dot Collector",
            5 to "Target Enthusiast",
            7 to "Bullseye Specialist",
            10 to "Dot Overlord",
            14 to "Cosmic Dot Vault",
            18 to "Celestial Reticles",
            22 to "Master of Sights",
            28 to "Prism Spotter",
            35 to "Mythic Target King",
            45 to "Supreme Reticle Lord",
            55 to "Grandmaster of Dots",
            70 to "Singularity Spotter",
            85 to "Sovereign Targeteer",
            100 to "Complete Dot Pantheon"
        )
        for (i in dotSkinTargets.indices) {
            val (target, title) = dotSkinTargets[i]
            val xp = (50 + i * 10).coerceAtMost(200)
            add(
                ArrowMission(
                    id = "mission_dot_skins_${i + 1}",
                    title = title,
                    desc = "Buy and unlock at least $target Dot Skins in the shop.",
                    xpReward = xp,
                    targetValue = target,
                    checkProgress = { _, _, _, dotsSize, _, _, _, _ -> dotsSize.coerceAtMost(target) }
                )
            )
        }

        // --- 8. LEVEL & RANK ASCENSION (Missions 116 to 125) ---
        val levelMilestones = listOf(
            1 to "Level 1: Novice Ascendant",
            2 to "Level 2: Swift Challenger",
            3 to "Level 3: Nimble Scout",
            5 to "Level 5: Precision Rank",
            8 to "Level 8: Vector Knight",
            10 to "Level 10: Supreme Ascendant",
            15 to "Level 15: Celestial Vanguard",
            20 to "Level 20: Eternal Legend",
            30 to "Level 30: Omni Sovereign",
            50 to "Level 50: Infinite Deity"
        )
        for (i in levelMilestones.indices) {
            val (lvl, title) = levelMilestones[i]
            val xp = (60 + i * 14).coerceAtMost(200)
            add(
                ArrowMission(
                    id = "mission_level_${i + 1}",
                    title = title,
                    desc = "Reach Player Level $lvl by earning XP.",
                    xpReward = xp,
                    targetValue = lvl,
                    checkProgress = { _, _, _, _, _, _, _, currentLevel -> currentLevel.coerceAtMost(lvl) }
                )
            )
        }
    }
}
