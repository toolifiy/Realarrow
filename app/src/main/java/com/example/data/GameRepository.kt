package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.model.GameStats
import com.example.model.MissionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class GameRepository(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("arrow_reflex_prefs", Context.MODE_PRIVATE)

    private val _coins = MutableStateFlow(
        prefs.getInt(KEY_COINS, 1000000).let { current ->
            if (current < 1000000) {
                prefs.edit().putInt(KEY_COINS, 1000000).apply()
                1000000
            } else {
                current
            }
        }
    )
    val coins: StateFlow<Int> = _coins.asStateFlow()

    private val _coinsSpent = MutableStateFlow(prefs.getInt(KEY_COINS_SPENT, 0))
    val coinsSpent: StateFlow<Int> = _coinsSpent.asStateFlow()

    private val _bestTimeMs = MutableStateFlow(prefs.getLong(KEY_BEST_TIME, 0L))
    val bestTimeMs: StateFlow<Long> = _bestTimeMs.asStateFlow()

    private val _totalHits = MutableStateFlow(prefs.getInt(KEY_TOTAL_HITS, 0))
    val totalHits: StateFlow<Int> = _totalHits.asStateFlow()

    private val _equippedSkinId = MutableStateFlow(prefs.getString(KEY_EQUIPPED_SKIN, "skin_classic") ?: "skin_classic")
    val equippedSkinId: StateFlow<String> = _equippedSkinId.asStateFlow()

    private val _unlockedSkinIds = MutableStateFlow(
        prefs.getStringSet(KEY_UNLOCKED_SKINS, setOf("skin_classic")) ?: setOf("skin_classic")
    )
    val unlockedSkinIds: StateFlow<Set<String>> = _unlockedSkinIds.asStateFlow()

    private val _equippedDotId = MutableStateFlow(prefs.getString(KEY_EQUIPPED_DOT, "dot_classic") ?: "dot_classic")
    val equippedDotId: StateFlow<String> = _equippedDotId.asStateFlow()

    private val _unlockedDotIds = MutableStateFlow(
        prefs.getStringSet(KEY_UNLOCKED_DOTS, setOf("dot_classic")) ?: setOf("dot_classic")
    )
    val unlockedDotIds: StateFlow<Set<String>> = _unlockedDotIds.asStateFlow()

    private val _soundEnabled = MutableStateFlow(prefs.getBoolean(KEY_SOUND_ENABLED, true))
    val soundEnabled: StateFlow<Boolean> = _soundEnabled.asStateFlow()

    private val _hapticEnabled = MutableStateFlow(prefs.getBoolean(KEY_HAPTIC_ENABLED, true))
    val hapticEnabled: StateFlow<Boolean> = _hapticEnabled.asStateFlow()

    private val _showArrow = MutableStateFlow(prefs.getBoolean(KEY_SHOW_ARROW, true))
    val showArrow: StateFlow<Boolean> = _showArrow.asStateFlow()

    private val _showDot = MutableStateFlow(prefs.getBoolean(KEY_SHOW_DOT, true))
    val showDot: StateFlow<Boolean> = _showDot.asStateFlow()

    private val _alignCenter = MutableStateFlow(prefs.getBoolean(KEY_ALIGN_CENTER, true))
    val alignCenter: StateFlow<Boolean> = _alignCenter.asStateFlow()

    private val _totalXp = MutableStateFlow(prefs.getInt(KEY_TOTAL_XP, 0))
    val totalXp: StateFlow<Int> = _totalXp.asStateFlow()

    private val _gamesPlayed = MutableStateFlow(prefs.getInt(KEY_GAMES_PLAYED, 0))
    val gamesPlayed: StateFlow<Int> = _gamesPlayed.asStateFlow()

    // Hearts state flow (Max 5, restored daily to 5)
    private val _hearts = MutableStateFlow(5)
    val hearts: StateFlow<Int> = _hearts.asStateFlow()

    // Daily Stats
    private val _dailyHits = MutableStateFlow(0)
    val dailyHits: StateFlow<Int> = _dailyHits.asStateFlow()

    private val _dailyGames = MutableStateFlow(0)
    val dailyGames: StateFlow<Int> = _dailyGames.asStateFlow()

    private val _dailyBestTimeMs = MutableStateFlow(0L)
    val dailyBestTimeMs: StateFlow<Long> = _dailyBestTimeMs.asStateFlow()

    private val _dailyCoinsEarned = MutableStateFlow(0)
    val dailyCoinsEarned: StateFlow<Int> = _dailyCoinsEarned.asStateFlow()

    // Weekly Stats
    private val _weeklyHits = MutableStateFlow(0)
    val weeklyHits: StateFlow<Int> = _weeklyHits.asStateFlow()

    private val _weeklyGames = MutableStateFlow(0)
    val weeklyGames: StateFlow<Int> = _weeklyGames.asStateFlow()

    private val _weeklyBestTimeMs = MutableStateFlow(0L)
    val weeklyBestTimeMs: StateFlow<Long> = _weeklyBestTimeMs.asStateFlow()

    private val _weeklyCoinsEarned = MutableStateFlow(0)
    val weeklyCoinsEarned: StateFlow<Int> = _weeklyCoinsEarned.asStateFlow()

    // Claimed sets
    private var claimedPermanentMissions: MutableSet<String> = mutableSetOf()
    private var claimedDailyMissions: MutableSet<String> = mutableSetOf()
    private var claimedWeeklyMissions: MutableSet<String> = mutableSetOf()

    private val _claimedMissions = MutableStateFlow<Set<String>>(emptySet())
    val claimedMissions: StateFlow<Set<String>> = _claimedMissions.asStateFlow()

    init {
        checkDailyAndWeeklyResets()
    }

    private fun getTodayDateKey(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
    }

    private fun getCurrentWeekKey(): String {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val week = cal.get(Calendar.WEEK_OF_YEAR)
        return "$year-W$week"
    }

    private fun checkDailyAndWeeklyResets() {
        val today = getTodayDateKey()
        val currentWeek = getCurrentWeekKey()

        val lastDailyDate = prefs.getString(KEY_LAST_DAILY_DATE, "") ?: ""
        val lastWeekKey = prefs.getString(KEY_LAST_WEEKLY_KEY, "") ?: ""

        // Permanent claimed missions
        claimedPermanentMissions = (prefs.getStringSet(KEY_CLAIMED_PERM_MISSIONS, emptySet()) ?: emptySet()).toMutableSet()

        // 1. Daily Reset Check
        if (today != lastDailyDate) {
            // Reset daily counters & hearts & daily claimed missions
            prefs.edit()
                .putString(KEY_LAST_DAILY_DATE, today)
                .putInt(KEY_HEARTS, 5)
                .putInt(KEY_DAILY_HITS, 0)
                .putInt(KEY_DAILY_GAMES, 0)
                .putLong(KEY_DAILY_BEST_TIME, 0L)
                .putInt(KEY_DAILY_COINS, 0)
                .putStringSet(KEY_CLAIMED_DAILY_MISSIONS, emptySet())
                .apply()

            _hearts.value = 5
            _dailyHits.value = 0
            _dailyGames.value = 0
            _dailyBestTimeMs.value = 0L
            _dailyCoinsEarned.value = 0
            claimedDailyMissions = mutableSetOf()
        } else {
            _hearts.value = prefs.getInt(KEY_HEARTS, 5)
            _dailyHits.value = prefs.getInt(KEY_DAILY_HITS, 0)
            _dailyGames.value = prefs.getInt(KEY_DAILY_GAMES, 0)
            _dailyBestTimeMs.value = prefs.getLong(KEY_DAILY_BEST_TIME, 0L)
            _dailyCoinsEarned.value = prefs.getInt(KEY_DAILY_COINS, 0)
            claimedDailyMissions = (prefs.getStringSet(KEY_CLAIMED_DAILY_MISSIONS, emptySet()) ?: emptySet()).toMutableSet()
        }

        // 2. Weekly Reset Check
        if (currentWeek != lastWeekKey) {
            // Reset weekly counters & weekly claimed missions
            prefs.edit()
                .putString(KEY_LAST_WEEKLY_KEY, currentWeek)
                .putInt(KEY_WEEKLY_HITS, 0)
                .putInt(KEY_WEEKLY_GAMES, 0)
                .putLong(KEY_WEEKLY_BEST_TIME, 0L)
                .putInt(KEY_WEEKLY_COINS, 0)
                .putStringSet(KEY_CLAIMED_WEEKLY_MISSIONS, emptySet())
                .apply()

            _weeklyHits.value = 0
            _weeklyGames.value = 0
            _weeklyBestTimeMs.value = 0L
            _weeklyCoinsEarned.value = 0
            claimedWeeklyMissions = mutableSetOf()
        } else {
            _weeklyHits.value = prefs.getInt(KEY_WEEKLY_HITS, 0)
            _weeklyGames.value = prefs.getInt(KEY_WEEKLY_GAMES, 0)
            _weeklyBestTimeMs.value = prefs.getLong(KEY_WEEKLY_BEST_TIME, 0L)
            _weeklyCoinsEarned.value = prefs.getInt(KEY_WEEKLY_COINS, 0)
            claimedWeeklyMissions = (prefs.getStringSet(KEY_CLAIMED_WEEKLY_MISSIONS, emptySet()) ?: emptySet()).toMutableSet()
        }

        _claimedMissions.value = claimedPermanentMissions + claimedDailyMissions + claimedWeeklyMissions
    }

    fun useHeart(): Boolean {
        val current = _hearts.value
        if (current > 0) {
            val next = current - 1
            prefs.edit().putInt(KEY_HEARTS, next).apply()
            _hearts.value = next
            return true
        }
        return false
    }

    fun setHearts(amount: Int) {
        val next = amount.coerceIn(0, 5)
        prefs.edit().putInt(KEY_HEARTS, next).apply()
        _hearts.value = next
    }

    fun addHeart(amount: Int = 1) {
        val next = (_hearts.value + amount).coerceAtMost(5)
        prefs.edit().putInt(KEY_HEARTS, next).apply()
        _hearts.value = next
    }

    fun setSoundEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_SOUND_ENABLED, enabled).apply()
        _soundEnabled.value = enabled
    }

    fun setHapticEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_HAPTIC_ENABLED, enabled).apply()
        _hapticEnabled.value = enabled
    }

    fun setShowArrow(enabled: Boolean) {
        var finalEnabled = enabled
        if (!finalEnabled && !_showDot.value) {
            prefs.edit().putBoolean(KEY_SHOW_DOT, true).apply()
            _showDot.value = true
        }
        prefs.edit().putBoolean(KEY_SHOW_ARROW, finalEnabled).apply()
        _showArrow.value = finalEnabled
    }

    fun setShowDot(enabled: Boolean) {
        var finalEnabled = enabled
        if (!finalEnabled && !_showArrow.value) {
            prefs.edit().putBoolean(KEY_SHOW_ARROW, true).apply()
            _showArrow.value = true
        }
        prefs.edit().putBoolean(KEY_SHOW_DOT, finalEnabled).apply()
        _showDot.value = finalEnabled
    }

    fun setAlignCenter(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_ALIGN_CENTER, enabled).apply()
        _alignCenter.value = enabled
    }

    fun resetGameStats() {
        prefs.edit()
            .putLong(KEY_BEST_TIME, 0L)
            .putInt(KEY_TOTAL_HITS, 0)
            .apply()
        _bestTimeMs.value = 0L
        _totalHits.value = 0
    }

    fun addCoins(amount: Int = 1) {
        checkDailyAndWeeklyResets()
        val newCoins = _coins.value + amount
        val newDailyCoins = _dailyCoinsEarned.value + amount
        val newWeeklyCoins = _weeklyCoinsEarned.value + amount

        prefs.edit()
            .putInt(KEY_COINS, newCoins)
            .putInt(KEY_DAILY_COINS, newDailyCoins)
            .putInt(KEY_WEEKLY_COINS, newWeeklyCoins)
            .apply()

        _coins.value = newCoins
        _dailyCoinsEarned.value = newDailyCoins
        _weeklyCoinsEarned.value = newWeeklyCoins
    }

    fun deductCoins(amount: Int): Boolean {
        if (_coins.value >= amount) {
            val newCoins = _coins.value - amount
            val newSpent = _coinsSpent.value + amount
            prefs.edit()
                .putInt(KEY_COINS, newCoins)
                .putInt(KEY_COINS_SPENT, newSpent)
                .apply()
            _coins.value = newCoins
            _coinsSpent.value = newSpent
            return true
        }
        return false
    }

    fun recordReactionTime(timeMs: Long) {
        checkDailyAndWeeklyResets()

        // 1. Total Hits & Best Lifetime Reaction
        val currentBest = _bestTimeMs.value
        if (currentBest == 0L || timeMs < currentBest) {
            prefs.edit().putLong(KEY_BEST_TIME, timeMs).apply()
            _bestTimeMs.value = timeMs
        }
        val newHits = _totalHits.value + 1
        _totalHits.value = newHits

        // 2. Daily Hits & Best Daily Reaction
        val newDailyHits = _dailyHits.value + 1
        _dailyHits.value = newDailyHits
        val currentDailyBest = _dailyBestTimeMs.value
        val newDailyBest = if (currentDailyBest == 0L || timeMs < currentDailyBest) timeMs else currentDailyBest
        _dailyBestTimeMs.value = newDailyBest

        // 3. Weekly Hits & Best Weekly Reaction
        val newWeeklyHits = _weeklyHits.value + 1
        _weeklyHits.value = newWeeklyHits
        val currentWeeklyBest = _weeklyBestTimeMs.value
        val newWeeklyBest = if (currentWeeklyBest == 0L || timeMs < currentWeeklyBest) timeMs else currentWeeklyBest
        _weeklyBestTimeMs.value = newWeeklyBest

        prefs.edit()
            .putInt(KEY_TOTAL_HITS, newHits)
            .putInt(KEY_DAILY_HITS, newDailyHits)
            .putLong(KEY_DAILY_BEST_TIME, newDailyBest)
            .putInt(KEY_WEEKLY_HITS, newWeeklyHits)
            .putLong(KEY_WEEKLY_BEST_TIME, newWeeklyBest)
            .apply()
    }

    fun unlockSkin(skinId: String): Boolean {
        val current = _unlockedSkinIds.value.toMutableSet()
        if (!current.contains(skinId)) {
            current.add(skinId)
            prefs.edit().putStringSet(KEY_UNLOCKED_SKINS, current).apply()
            _unlockedSkinIds.value = current
            return true
        }
        return false
    }

    fun equipSkin(skinId: String) {
        prefs.edit().putString(KEY_EQUIPPED_SKIN, skinId).apply()
        _equippedSkinId.value = skinId
    }

    fun unlockDot(dotId: String): Boolean {
        val current = _unlockedDotIds.value.toMutableSet()
        if (!current.contains(dotId)) {
            current.add(dotId)
            prefs.edit().putStringSet(KEY_UNLOCKED_DOTS, current).apply()
            _unlockedDotIds.value = current
            return true
        }
        return false
    }

    fun equipDot(dotId: String) {
        prefs.edit().putString(KEY_EQUIPPED_DOT, dotId).apply()
        _equippedDotId.value = dotId
    }

    fun addXp(amount: Int) {
        val newXp = _totalXp.value + amount
        prefs.edit().putInt(KEY_TOTAL_XP, newXp).apply()
        _totalXp.value = newXp
    }

    fun incrementGamesPlayed() {
        checkDailyAndWeeklyResets()
        val newGames = _gamesPlayed.value + 1
        val newDailyGames = _dailyGames.value + 1
        val newWeeklyGames = _weeklyGames.value + 1

        prefs.edit()
            .putInt(KEY_GAMES_PLAYED, newGames)
            .putInt(KEY_DAILY_GAMES, newDailyGames)
            .putInt(KEY_WEEKLY_GAMES, newWeeklyGames)
            .apply()

        _gamesPlayed.value = newGames
        _dailyGames.value = newDailyGames
        _weeklyGames.value = newWeeklyGames
    }

    fun claimMission(missionId: String, type: MissionType) {
        when (type) {
            MissionType.STARTER -> {
                claimedPermanentMissions.add(missionId)
                prefs.edit().putStringSet(KEY_CLAIMED_PERM_MISSIONS, claimedPermanentMissions).apply()
            }
            MissionType.DAILY -> {
                claimedDailyMissions.add(missionId)
                prefs.edit().putStringSet(KEY_CLAIMED_DAILY_MISSIONS, claimedDailyMissions).apply()
            }
            MissionType.WEEKLY -> {
                claimedWeeklyMissions.add(missionId)
                prefs.edit().putStringSet(KEY_CLAIMED_WEEKLY_MISSIONS, claimedWeeklyMissions).apply()
            }
        }
        _claimedMissions.value = claimedPermanentMissions + claimedDailyMissions + claimedWeeklyMissions
    }

    fun getFullGameStats(currentLevel: Int): GameStats {
        return GameStats(
            totalHits = _totalHits.value,
            bestTimeMs = _bestTimeMs.value,
            skinsSize = _unlockedSkinIds.value.size,
            dotsSize = _unlockedDotIds.value.size,
            coins = _coins.value,
            coinsSpent = _coinsSpent.value,
            gamesPlayed = _gamesPlayed.value,
            currentLevel = currentLevel,
            dailyHits = _dailyHits.value,
            dailyGames = _dailyGames.value,
            dailyBestTimeMs = _dailyBestTimeMs.value,
            dailyCoinsEarned = _dailyCoinsEarned.value,
            weeklyHits = _weeklyHits.value,
            weeklyGames = _weeklyGames.value,
            weeklyBestTimeMs = _weeklyBestTimeMs.value,
            weeklyCoinsEarned = _weeklyCoinsEarned.value
        )
    }

    companion object {
        private const val KEY_COINS = "user_coins"
        private const val KEY_COINS_SPENT = "user_coins_spent"
        private const val KEY_BEST_TIME = "user_best_time_ms"
        private const val KEY_TOTAL_HITS = "user_total_hits"
        private const val KEY_EQUIPPED_SKIN = "equipped_skin_id"
        private const val KEY_UNLOCKED_SKINS = "unlocked_skin_ids_set"
        private const val KEY_EQUIPPED_DOT = "equipped_dot_id"
        private const val KEY_UNLOCKED_DOTS = "unlocked_dot_ids_set"
        private const val KEY_SOUND_ENABLED = "sound_effects_enabled"
        private const val KEY_HAPTIC_ENABLED = "haptic_feedback_enabled"
        private const val KEY_HEARTS = "user_hearts_count"
        private const val KEY_SHOW_ARROW = "settings_show_arrow"
        private const val KEY_SHOW_DOT = "settings_show_dot"
        private const val KEY_ALIGN_CENTER = "settings_align_center"
        private const val KEY_TOTAL_XP = "user_total_xp"
        private const val KEY_GAMES_PLAYED = "user_games_played"

        private const val KEY_LAST_DAILY_DATE = "last_daily_reset_date"
        private const val KEY_LAST_WEEKLY_KEY = "last_weekly_reset_key"

        private const val KEY_DAILY_HITS = "daily_user_hits"
        private const val KEY_DAILY_GAMES = "daily_user_games"
        private const val KEY_DAILY_BEST_TIME = "daily_user_best_time"
        private const val KEY_DAILY_COINS = "daily_user_coins_earned"

        private const val KEY_WEEKLY_HITS = "weekly_user_hits"
        private const val KEY_WEEKLY_GAMES = "weekly_user_games"
        private const val KEY_WEEKLY_BEST_TIME = "weekly_user_best_time"
        private const val KEY_WEEKLY_COINS = "weekly_user_coins_earned"

        private const val KEY_CLAIMED_PERM_MISSIONS = "claimed_permanent_missions"
        private const val KEY_CLAIMED_DAILY_MISSIONS = "claimed_daily_missions"
        private const val KEY_CLAIMED_WEEKLY_MISSIONS = "claimed_weekly_missions"
    }
}
