package com.example.viewmodel

import android.app.Application
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.GameRepository
import com.example.model.ArrowMission
import com.example.model.ArrowMissionCatalog
import com.example.model.ArrowSkin
import com.example.model.ArrowSkinCatalog
import com.example.model.DotSkin
import com.example.model.DotSkinCatalog
import com.example.model.GameStats
import com.example.util.SoundManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppScreen {
    HOME,
    GAME,
    SHOP
}

data class GameUiState(
    val screen: AppScreen = AppScreen.HOME,
    val isArrowVisible: Boolean = true,
    val lastReactionTimeMs: Long? = null,
    val showReactionOverlay: Boolean = false,
    val showBrokenHeartOverlay: Boolean = false, // 0.5s broken heart on bad click
    val lastHitOffset: Offset? = null,
    val showCoinPopup: Boolean = false,
    val message: String? = null,
    val showOutPopup: Boolean = false // If true, triggers 1.3s out of hearts countdown
)

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = GameRepository(application)
    private val soundManager = SoundManager(application)

    val coins: StateFlow<Int> = repository.coins
    val coinsSpent: StateFlow<Int> = repository.coinsSpent
    val bestTimeMs: StateFlow<Long> = repository.bestTimeMs
    val totalHits: StateFlow<Int> = repository.totalHits
    val unlockedSkinIds: StateFlow<Set<String>> = repository.unlockedSkinIds
    val equippedSkinId: StateFlow<String> = repository.equippedSkinId
    val soundEnabled: StateFlow<Boolean> = repository.soundEnabled
    val hapticEnabled: StateFlow<Boolean> = repository.hapticEnabled
    val showArrow: StateFlow<Boolean> = repository.showArrow
    val showDot: StateFlow<Boolean> = repository.showDot
    val alignCenter: StateFlow<Boolean> = repository.alignCenter
    val hearts: StateFlow<Int> = repository.hearts
    val totalXp: StateFlow<Int> = repository.totalXp
    val gamesPlayed: StateFlow<Int> = repository.gamesPlayed
    val claimedMissions: StateFlow<Set<String>> = repository.claimedMissions

    val equippedSkin: StateFlow<ArrowSkin> = repository.equippedSkinId
        .combine(repository.unlockedSkinIds) { id, _ ->
            ArrowSkinCatalog.getSkinById(id)
        }.stateIn(viewModelScope, SharingStarted.Eagerly, ArrowSkinCatalog.CLASSIC)

    val unlockedDotIds: StateFlow<Set<String>> = repository.unlockedDotIds
    val equippedDotId: StateFlow<String> = repository.equippedDotId

    val equippedDot: StateFlow<DotSkin> = repository.equippedDotId
        .combine(repository.unlockedDotIds) { id, _ ->
            DotSkinCatalog.getSkinById(id)
        }.stateIn(viewModelScope, SharingStarted.Eagerly, DotSkinCatalog.CLASSIC)

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var respawnJob: Job? = null

    init {
        // Collect sound & haptic configurations to soundManager
        viewModelScope.launch {
            soundEnabled.collect { enabled ->
                soundManager.setSoundEnabled(enabled)
            }
        }
        viewModelScope.launch {
            hapticEnabled.collect { enabled ->
                soundManager.setHapticEnabled(enabled)
            }
        }
    }

    fun setSoundEnabled(enabled: Boolean) {
        repository.setSoundEnabled(enabled)
    }

    fun setHapticEnabled(enabled: Boolean) {
        repository.setHapticEnabled(enabled)
    }

    fun setShowArrow(enabled: Boolean) {
        repository.setShowArrow(enabled)
    }

    fun setShowDot(enabled: Boolean) {
        repository.setShowDot(enabled)
    }

    fun setAlignCenter(enabled: Boolean) {
        repository.setAlignCenter(enabled)
    }

    fun resetStats() {
        repository.resetGameStats()
        _uiState.value = _uiState.value.copy(message = "Stats reset successfully!")
    }

    fun navigateTo(screen: AppScreen) {
        respawnJob?.cancel()
        if (screen == AppScreen.GAME) {
            repository.incrementGamesPlayed()
            val hasHearts = repository.hearts.value > 0
            _uiState.value = _uiState.value.copy(
                screen = screen,
                isArrowVisible = hasHearts,
                showReactionOverlay = false,
                showCoinPopup = false,
                showOutPopup = !hasHearts
            )
        } else {
            _uiState.value = _uiState.value.copy(screen = screen)
        }
    }

    fun onArrowSpawned() {
        soundManager.playSpawnTick()
    }

    fun onTipHit(reactionTimeMs: Long, tipOffset: Offset) {
        respawnJob?.cancel()

        // 1. Play success effects
        soundManager.playSuccessTick()
        soundManager.playHitFeedback()

        // 2. Add +1 Coin & +5 XP per hit
        repository.addCoins(1)
        repository.addXp(5)

        // 3. Record reaction time & check best record
        repository.recordReactionTime(reactionTimeMs)

        // 4. Hide arrow and show reaction time details for exactly 0.5s (500ms)
        _uiState.value = _uiState.value.copy(
            isArrowVisible = false,
            lastReactionTimeMs = reactionTimeMs,
            showReactionOverlay = true,
            lastHitOffset = tipOffset,
            showCoinPopup = true
        )

        respawnJob = viewModelScope.launch {
            delay(500L) // Exact .5 second delay requested by user
            _uiState.value = _uiState.value.copy(
                isArrowVisible = true,
                showReactionOverlay = false,
                showCoinPopup = false
            )
        }
    }

    fun onMissedTap(offset: Offset) {
        respawnJob?.cancel()
        soundManager.playMissFeedback()

        val currentHearts = repository.hearts.value
        if (currentHearts > 0) {
            // Deduct 1 Heart
            repository.useHeart()
            val remainingHearts = repository.hearts.value

            if (remainingHearts <= 0) {
                // Out of hearts completely (meaning mistake #5 occurred, hearts dropped to 0)
                soundManager.playGameOverSound() // Play the 0.5s "dhidhid dhudhum tadak" crash sound!
                _uiState.value = _uiState.value.copy(
                    isArrowVisible = false,
                    showReactionOverlay = false,
                    showCoinPopup = false,
                    showOutPopup = true
                )
            } else {
                // Heart deducted, play the loud distinct wrong click warning buzzer!
                soundManager.playWrongClick()
                _uiState.value = _uiState.value.copy(
                    isArrowVisible = false,
                    showReactionOverlay = false,
                    showCoinPopup = false,
                    showBrokenHeartOverlay = true
                )
                respawnJob = viewModelScope.launch {
                    delay(500L) // 0.5s broken heart display
                    _uiState.value = _uiState.value.copy(
                        isArrowVisible = true,
                        showBrokenHeartOverlay = false
                    )
                }
            }
        } else {
            // Out of hearts completely! Show the animated broken heart popup and play Game Over sound
            soundManager.playGameOverSound()
            _uiState.value = _uiState.value.copy(
                isArrowVisible = false,
                showReactionOverlay = false,
                showCoinPopup = false,
                showOutPopup = true
            )
        }
    }

    fun onTailHit(offset: Offset) {
        onMissedTap(offset)
    }

    fun dismissOutPopup() {
        _uiState.value = _uiState.value.copy(showOutPopup = false)
    }

    fun onAdCompleted() {
        // Watch ad completed -> Grant exactly 1 Heart!
        repository.setHearts(1)
        _uiState.value = _uiState.value.copy(
            showOutPopup = false,
            isArrowVisible = true
        )
    }

    fun buySkin(skin: ArrowSkin) {
        if (unlockedSkinIds.value.contains(skin.id)) {
            repository.equipSkin(skin.id)
            _uiState.value = _uiState.value.copy(message = "Equipped ${skin.name}!")
            return
        }

        if (repository.deductCoins(skin.price)) {
            repository.unlockSkin(skin.id)
            repository.equipSkin(skin.id)
            _uiState.value = _uiState.value.copy(message = "Unlocked & Equipped ${skin.name}!")
        } else {
            val needed = skin.price - coins.value
            _uiState.value = _uiState.value.copy(message = "Need $needed more coins to unlock!")
        }
    }

    fun equipSkin(skinId: String) {
        if (unlockedSkinIds.value.contains(skinId)) {
            repository.equipSkin(skinId)
            val skin = ArrowSkinCatalog.getSkinById(skinId)
            _uiState.value = _uiState.value.copy(message = "Equipped ${skin.name}!")
        }
    }

    fun buyDot(dot: DotSkin) {
        if (unlockedDotIds.value.contains(dot.id)) {
            repository.equipDot(dot.id)
            _uiState.value = _uiState.value.copy(message = "Equipped ${dot.name}!")
            return
        }

        if (repository.deductCoins(dot.price)) {
            repository.unlockDot(dot.id)
            repository.equipDot(dot.id)
            _uiState.value = _uiState.value.copy(message = "Unlocked & Equipped ${dot.name}!")
        } else {
            val needed = dot.price - coins.value
            _uiState.value = _uiState.value.copy(message = "Need $needed more coins to unlock!")
        }
    }

    fun equipDot(dotId: String) {
        if (unlockedDotIds.value.contains(dotId)) {
            repository.equipDot(dotId)
            val dot = DotSkinCatalog.getSkinById(dotId)
            _uiState.value = _uiState.value.copy(message = "Equipped ${dot.name}!")
        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }

    fun getGameStats(): GameStats {
        val currentLevel = repository.totalXp.value / 1000
        return repository.getFullGameStats(currentLevel)
    }

    fun claimMission(mission: ArrowMission) {
        val claimed = repository.claimedMissions.value
        if (!claimed.contains(mission.id)) {
            repository.claimMission(mission.id, mission.type)
            repository.addXp(mission.xpReward)
            if (mission.coinReward > 0) {
                repository.addCoins(mission.coinReward)
            }
            val bonusMsg = if (mission.coinReward > 0) " & +${mission.coinReward} Coins" else ""
            _uiState.value = _uiState.value.copy(message = "Claimed +${mission.xpReward} XP$bonusMsg!")
        }
    }

    fun claimMissionXp(missionId: String, xpReward: Int) {
        val claimed = repository.claimedMissions.value
        if (!claimed.contains(missionId)) {
            val mission = ArrowMissionCatalog.allMissions.find { it.id == missionId }
            if (mission != null) {
                claimMission(mission)
            } else {
                repository.claimMission(missionId, com.example.model.MissionType.STARTER)
                repository.addXp(xpReward)
                _uiState.value = _uiState.value.copy(message = "Claimed +$xpReward XP!")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        soundManager.release()
    }
}
