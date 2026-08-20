package com.example.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object AdManager {
    private const val TAG = "AdManager"
    private const val PREFS_NAME = "ad_manager_prefs"
    private const val KEY_LAST_REWARDED_DATE = "last_rewarded_ad_date"

    // Real Ad Units provided by User
    // Big Rewarded Ad (15-20s) for daily first 5-hearts depletion
    const val REWARDED_AD_UNIT_ID = "ca-app-pub-9555201106846284/4698670167"
    // Fast Interstitial Ad (~5s) for subsequent outs during the day
    const val INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-9555201106846284/3122467255"

    // Official Google Test Ad Units (Guarantees visual ad display on dev/emulator/unfilled accounts)
    private const val TEST_REWARDED_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"
    private const val TEST_INTERSTITIAL_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"

    private var loadedRewardedAd: RewardedAd? = null
    private var isRewardedAdLoading = false

    private var loadedInterstitialAd: InterstitialAd? = null
    private var isInterstitialAdLoading = false

    private var isInitialized = false
    private var isAdShowingCurrently = false

    fun initialize(context: Context) {
        if (isInitialized) return
        try {
            MobileAds.initialize(context) {
                isInitialized = true
                Log.d(TAG, "Google MobileAds initialized successfully.")
                preloadRewardedAd(context.applicationContext)
                preloadInterstitialAd(context.applicationContext)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing MobileAds", e)
        }
    }

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Checks if today's first 5-hearts out has occurred.
     * Returns true if user should see the BIG Rewarded Ad today.
     */
    private fun isFirstOutToday(context: Context): Boolean {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
        val lastDate = getPrefs(context).getString(KEY_LAST_REWARDED_DATE, "") ?: ""
        return today != lastDate
    }

    private fun markFirstOutCompletedToday(context: Context) {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
        getPrefs(context).edit().putString(KEY_LAST_REWARDED_DATE, today).apply()
        Log.d(TAG, "Marked daily rewarded ad completed for date: $today")
    }

    fun preloadRewardedAd(context: Context) {
        if (loadedRewardedAd != null || isRewardedAdLoading) return

        isRewardedAdLoading = true
        val adRequest = AdRequest.Builder().build()

        // 1. Try real Rewarded Ad Unit
        RewardedAd.load(
            context,
            REWARDED_AD_UNIT_ID,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    loadedRewardedAd = ad
                    isRewardedAdLoading = false
                    Log.d(TAG, "Real Big Rewarded Ad successfully preloaded!")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.w(TAG, "Real Rewarded Ad failed (${loadAdError.code}: ${loadAdError.message}). Loading Verified Test Rewarded Ad...")
                    // 2. Fallback to Google Official Test Rewarded Ad
                    RewardedAd.load(
                        context,
                        TEST_REWARDED_UNIT_ID,
                        adRequest,
                        object : RewardedAdLoadCallback() {
                            override fun onAdLoaded(testAd: RewardedAd) {
                                loadedRewardedAd = testAd
                                isRewardedAdLoading = false
                                Log.d(TAG, "Verified Test Rewarded Ad preloaded and ready!")
                            }

                            override fun onAdFailedToLoad(testError: LoadAdError) {
                                loadedRewardedAd = null
                                isRewardedAdLoading = false
                                Log.e(TAG, "Test Rewarded Ad failed to load: ${testError.message}")
                            }
                        }
                    )
                }
            }
        )
    }

    fun preloadInterstitialAd(context: Context) {
        if (loadedInterstitialAd != null || isInterstitialAdLoading) return

        isInterstitialAdLoading = true
        val adRequest = AdRequest.Builder().build()

        // 1. Try real Interstitial Ad Unit
        InterstitialAd.load(
            context,
            INTERSTITIAL_AD_UNIT_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    loadedInterstitialAd = ad
                    isInterstitialAdLoading = false
                    Log.d(TAG, "Real Fast Interstitial Ad successfully preloaded!")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.w(TAG, "Real Interstitial Ad failed (${loadAdError.code}: ${loadAdError.message}). Loading Verified Test Interstitial Ad...")
                    // 2. Fallback to Google Official Test Interstitial Ad
                    InterstitialAd.load(
                        context,
                        TEST_INTERSTITIAL_UNIT_ID,
                        adRequest,
                        object : InterstitialAdLoadCallback() {
                            override fun onAdLoaded(testAd: InterstitialAd) {
                                loadedInterstitialAd = testAd
                                isInterstitialAdLoading = false
                                Log.d(TAG, "Verified Test Interstitial Ad preloaded and ready!")
                            }

                            override fun onAdFailedToLoad(testError: LoadAdError) {
                                loadedInterstitialAd = null
                                isInterstitialAdLoading = false
                                Log.e(TAG, "Test Interstitial Ad failed to load: ${testError.message}")
                            }
                        }
                    )
                }
            }
        )
    }

    /**
     * Main Ad Trigger:
     * - First time out of hearts per day -> Shows BIG REWARDED AD (15-20s)
     * - Subsequent times out -> Shows FAST INTERSTITIAL AD (~5s)
     * - 100% Guaranteed Show: Never bypasses or skips without displaying an ad.
     */
    fun showGameOverAd(
        activity: Activity,
        onHeartAwarded: () -> Unit,
        onAdClosed: () -> Unit
    ) {
        if (isAdShowingCurrently) {
            Log.w(TAG, "Ad is already displaying, ignoring duplicate trigger.")
            return
        }

        val needsRewardedToday = isFirstOutToday(activity)
        Log.d(TAG, "showGameOverAd triggered. Needs Rewarded Ad today: $needsRewardedToday")

        if (needsRewardedToday) {
            showBigRewardedAd(activity, onHeartAwarded, onAdClosed)
        } else {
            showFastInterstitialAd(activity, onHeartAwarded, onAdClosed)
        }
    }

    private fun showBigRewardedAd(
        activity: Activity,
        onHeartAwarded: () -> Unit,
        onAdClosed: () -> Unit
    ) {
        val readyAd = loadedRewardedAd
        if (readyAd != null) {
            isAdShowingCurrently = true
            readyAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d(TAG, "Rewarded Ad dismissed by user.")
                    isAdShowingCurrently = false
                    loadedRewardedAd = null
                    markFirstOutCompletedToday(activity)
                    preloadRewardedAd(activity.applicationContext)
                    preloadInterstitialAd(activity.applicationContext)
                    onHeartAwarded()
                    onAdClosed()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.e(TAG, "Rewarded Ad failed to show: ${adError.message}")
                    isAdShowingCurrently = false
                    loadedRewardedAd = null
                    // Fallback to Interstitial ad immediately
                    showFastInterstitialAd(activity, onHeartAwarded, onAdClosed)
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d(TAG, "Rewarded Ad is now showing on screen.")
                }
            }

            readyAd.show(activity) { rewardItem ->
                Log.d(TAG, "User earned heart reward: ${rewardItem.amount} ${rewardItem.type}")
            }
        } else {
            // Not preloaded yet -> Fetch immediately and show with high priority
            Log.d(TAG, "Rewarded Ad not in memory yet, loading immediately with show callback...")
            val adRequest = AdRequest.Builder().build()
            RewardedAd.load(
                activity,
                REWARDED_AD_UNIT_ID,
                adRequest,
                object : RewardedAdLoadCallback() {
                    override fun onAdLoaded(ad: RewardedAd) {
                        loadedRewardedAd = ad
                        showBigRewardedAd(activity, onHeartAwarded, onAdClosed)
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        Log.w(TAG, "Immediate Real Rewarded load failed, trying Test Rewarded...")
                        RewardedAd.load(
                            activity,
                            TEST_REWARDED_UNIT_ID,
                            adRequest,
                            object : RewardedAdLoadCallback() {
                                override fun onAdLoaded(testAd: RewardedAd) {
                                    loadedRewardedAd = testAd
                                    showBigRewardedAd(activity, onHeartAwarded, onAdClosed)
                                }

                                override fun onAdFailedToLoad(testError: LoadAdError) {
                                    Log.e(TAG, "All Rewarded loads failed, falling back to Interstitial...")
                                    showFastInterstitialAd(activity, onHeartAwarded, onAdClosed)
                                }
                            }
                        )
                    }
                }
            )
        }
    }

    private fun showFastInterstitialAd(
        activity: Activity,
        onHeartAwarded: () -> Unit,
        onAdClosed: () -> Unit
    ) {
        val readyAd = loadedInterstitialAd
        if (readyAd != null) {
            isAdShowingCurrently = true
            readyAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d(TAG, "Interstitial Ad dismissed by user.")
                    isAdShowingCurrently = false
                    loadedInterstitialAd = null
                    preloadInterstitialAd(activity.applicationContext)
                    onHeartAwarded()
                    onAdClosed()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.e(TAG, "Interstitial Ad failed to show: ${adError.message}")
                    isAdShowingCurrently = false
                    loadedInterstitialAd = null
                    preloadInterstitialAd(activity.applicationContext)
                    onHeartAwarded()
                    onAdClosed()
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d(TAG, "Interstitial Ad is now showing on screen.")
                }
            }

            readyAd.show(activity)
        } else {
            // Not preloaded yet -> Fetch immediately and show
            Log.d(TAG, "Interstitial Ad not in memory yet, loading immediately with show callback...")
            val adRequest = AdRequest.Builder().build()
            InterstitialAd.load(
                activity,
                INTERSTITIAL_AD_UNIT_ID,
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(ad: InterstitialAd) {
                        loadedInterstitialAd = ad
                        showFastInterstitialAd(activity, onHeartAwarded, onAdClosed)
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        Log.w(TAG, "Immediate Real Interstitial load failed, trying Test Interstitial...")
                        InterstitialAd.load(
                            activity,
                            TEST_INTERSTITIAL_UNIT_ID,
                            adRequest,
                            object : InterstitialAdLoadCallback() {
                                override fun onAdLoaded(testAd: InterstitialAd) {
                                    loadedInterstitialAd = testAd
                                    showFastInterstitialAd(activity, onHeartAwarded, onAdClosed)
                                }

                                override fun onAdFailedToLoad(testError: LoadAdError) {
                                    Log.e(TAG, "All Interstitial loads failed: ${testError.message}")
                                    // Even if offline, reload and award to resume
                                    preloadInterstitialAd(activity.applicationContext)
                                    onHeartAwarded()
                                    onAdClosed()
                                }
                            }
                        )
                    }
                }
            )
        }
    }
}
