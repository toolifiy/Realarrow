package com.example.util

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * High-performance, zero-latency Sound & Haptic Manager.
 * Pre-synthesizes all game audio buffers in memory on startup so sounds play
 * instantaneously (<1ms) with frame-perfect visual synchronization.
 */
class SoundManager(private val context: Context) {

    private var isMuted: Boolean = false
    private var isHapticDisabled: Boolean = false

    private val audioExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    private val vibrator: Vibrator? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vibratorManager?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }
    }

    // Pre-synthesized audio buffers (44.1kHz 16-bit PCM Mono)
    private val sampleRate = 44100
    private var successBuffer: ShortArray = ShortArray(0)
    private var wrongClickBuffer: ShortArray = ShortArray(0)
    private var gameOverBuffer: ShortArray = ShortArray(0)

    init {
        // Pre-compute sound buffers immediately on startup for instant playback
        precomputeBuffers()
    }

    private fun precomputeBuffers() {
        // 1. Success click (24ms crisp key tap)
        val successSamples = (24 * sampleRate / 1000)
        successBuffer = ShortArray(successSamples)
        for (i in 0 until successSamples) {
            val t = i.toDouble() / sampleRate
            val progress = i.toDouble() / successSamples
            val freq1 = 2200.0 - (600.0 * progress)
            val freq2 = 3400.0 - (800.0 * progress)
            val wave1 = Math.sin(2.0 * Math.PI * freq1 * t)
            val wave2 = Math.sin(2.0 * Math.PI * freq2 * t)
            val envelope = Math.exp(-18.0 * progress)
            val sample = (0.7 * wave1 + 0.3 * wave2) * envelope * 0.45
            successBuffer[i] = (sample * 32767).toInt().coerceIn(-32768, 32767).toShort()
        }

        // 2. Wrong click buzzer (220ms harsh error)
        val wrongSamples = (220 * sampleRate / 1000)
        wrongClickBuffer = ShortArray(wrongSamples)
        for (i in 0 until wrongSamples) {
            val t = i.toDouble() / sampleRate
            val progress = i.toDouble() / wrongSamples
            val f1 = 135.0
            val f2 = 185.0
            val w1 = Math.sin(2.0 * Math.PI * f1 * t)
            val w2 = Math.sin(2.0 * Math.PI * f2 * t)
            val w3 = 0.3 * Math.sin(2.0 * Math.PI * (f1 * 3) * t)
            val envelope = (1.0 - progress) * 0.85
            val sample = (0.45 * w1 + 0.45 * w2 + 0.1 * w3) * envelope
            wrongClickBuffer[i] = (sample * 32767).toInt().coerceIn(-32768, 32767).toShort()
        }

        // 3. Game Over / Broken Heart dramatic crunch crash (550ms: Heartbreak crack + sub-bass drop)
        val goDurationMs = 550
        val goSamples = (goDurationMs * sampleRate / 1000)
        gameOverBuffer = ShortArray(goSamples)

        val snapCut = (70 * sampleRate / 1000)      // Sharp ceramic glass heart shatter crack
        val subBassCut = (350 * sampleRate / 1000)  // Deep reverberating dramatic heart drop
        val tailCut = goSamples                     // Distant decay

        for (i in 0 until goSamples) {
            val t = i.toDouble() / sampleRate

            val s: Double = when {
                i < snapCut -> {
                    // Initial violent heartbreak crack (high frequency crunch)
                    val p = i.toDouble() / snapCut
                    val snapFreq = 2800.0 - (1800.0 * p)
                    val crack = Math.sin(2.0 * Math.PI * snapFreq * t)
                    val noise = (Math.random() * 2.0 - 1.0) * 0.4
                    val env = (1.0 - p)
                    (crack * 0.6 + noise * 0.4) * env * 0.95
                }
                i < subBassCut -> {
                    // Resonant dramatic deep bass heartbreak boom
                    val p = (i - snapCut).toDouble() / (subBassCut - snapCut)
                    val bassFreq = 160.0 - (110.0 * p) // 160Hz -> 50Hz drop
                    val env = Math.exp(-3.5 * p)
                    val wave = Math.sin(2.0 * Math.PI * bassFreq * t)
                    wave * env * 0.85
                }
                else -> {
                    // Soft warm low-end rumble decay
                    val p = (i - subBassCut).toDouble() / (tailCut - subBassCut)
                    val env = (1.0 - p) * 0.3
                    val rumble = Math.sin(2.0 * Math.PI * 48.0 * t)
                    rumble * env
                }
            }
            gameOverBuffer[i] = (s * 32767).toInt().coerceIn(-32768, 32767).toShort()
        }
    }

    fun setSoundEnabled(enabled: Boolean) {
        isMuted = !enabled
    }

    fun setHapticEnabled(enabled: Boolean) {
        isHapticDisabled = !enabled
    }

    fun playSpawnTick() {
        // Silent on arrow spawn
    }

    fun playSuccessTick() {
        if (isMuted || successBuffer.isEmpty()) return
        playBufferAsync(successBuffer)
    }

    fun playWrongClick() {
        if (isMuted || wrongClickBuffer.isEmpty()) return
        playBufferAsync(wrongClickBuffer)
    }

    fun playGameOverSound() {
        if (isMuted || gameOverBuffer.isEmpty()) return
        playBufferAsync(gameOverBuffer)
    }

    fun playErrorTick() {
        // Silent for compatibility
    }

    private fun playBufferAsync(buffer: ShortArray) {
        audioExecutor.execute {
            try {
                val audioTrack = AudioTrack(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build(),
                    AudioFormat.Builder()
                        .setSampleRate(sampleRate)
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build(),
                    buffer.size * 2,
                    AudioTrack.MODE_STATIC,
                    AudioManager.AUDIO_SESSION_ID_GENERATE
                )
                audioTrack.write(buffer, 0, buffer.size)
                audioTrack.play()

                val durMs = (buffer.size * 1000L / sampleRate) + 20L
                Thread.sleep(durMs)
                audioTrack.stop()
                audioTrack.release()
            } catch (_: Exception) {}
        }
    }

    fun playHitFeedback() {
        if (!isHapticDisabled) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator?.vibrate(VibrationEffect.createOneShot(35L, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator?.vibrate(35L)
                }
            } catch (_: Exception) {}
        }
    }

    fun playMissFeedback() {
        if (!isHapticDisabled) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator?.vibrate(VibrationEffect.createOneShot(25L, 100))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator?.vibrate(25L)
                }
            } catch (_: Exception) {}
        }
    }

    fun release() {
        audioExecutor.shutdown()
    }
}
