package com.example.util

import android.app.Activity
import android.content.Context
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

object AdManager {
    private const val TAG = "AdManager"

    // Real Ad Units provided by User
    const val FIRST_AD_UNIT_ID = "ca-app-pub-9555201106846284/4698670167"
    const val FAST_AD_UNIT_ID = "ca-app-pub-9555201106846284/3122467255"

    // Google Official Test Ad Units (Guaranteed to show in emulator / dev builds when real unit has no fill)
    private const val TEST_REWARDED_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"
    private const val TEST_INTERSTITIAL_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"

    private var firstRewardedAd: RewardedAd? = null
    private var firstInterstitialAd: InterstitialAd? = null
    private var isFirstAdLoading = false

    private var fastInterstitialAd: InterstitialAd? = null
    private var fastRewardedAd: RewardedAd? = null
    private var isFastAdLoading = false

    private var isInitialized = false

    // Track whether the first 5-hearts depletion ad has already been served in the session
    private var hasShownFirstDepletionAd = false

    fun initialize(context: Context) {
        if (isInitialized) return
        try {
            MobileAds.initialize(context) {
                isInitialized = true
                Log.d(TAG, "AdMob MobileAds initialized successfully.")
                loadAllAds(context.applicationContext)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing MobileAds", e)
        }
    }

    private fun loadAllAds(context: Context) {
        loadFirstAd(context)
        loadFastAd(context)
    }

    fun loadFirstAd(context: Context) {
        if (firstRewardedAd != null || firstInterstitialAd != null || isFirstAdLoading) return

        isFirstAdLoading = true
        val adRequest = AdRequest.Builder().build()

        // 1. Try real Rewarded Ad Unit
        RewardedAd.load(
            context,
            FIRST_AD_UNIT_ID,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    firstRewardedAd = ad
                    isFirstAdLoading = false
                    Log.d(TAG, "First Real Ad (Rewarded) successfully loaded!")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.w(TAG, "First Real Ad (Rewarded) failed (${loadAdError.code}: ${loadAdError.message}). Trying Real Interstitial...")
                    // 2. Try real Interstitial
                    InterstitialAd.load(
                        context,
                        FIRST_AD_UNIT_ID,
                        adRequest,
                        object : InterstitialAdLoadCallback() {
                            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                                firstInterstitialAd = interstitialAd
                                isFirstAdLoading = false
                                Log.d(TAG, "First Real Ad (Interstitial) loaded!")
                            }

                            override fun onAdFailedToLoad(error: LoadAdError) {
                                Log.w(TAG, "First Real Ad failed. Falling back to Google Verified Test Rewarded Unit: ${error.message}")
                                // 3. Fallback to Google Official Test Ad (Guarantees visual ad display on dev/emulator)
                                RewardedAd.load(
                                    context,
                                    TEST_REWARDED_UNIT_ID,
                                    adRequest,
                                    object : RewardedAdLoadCallback() {
                                        override fun onAdLoaded(testAd: RewardedAd) {
                                            firstRewardedAd = testAd
                                            isFirstAdLoading = false
                                            Log.d(TAG, "First Test Ad loaded and ready!")
                                        }

                                        override fun onAdFailedToLoad(testError: LoadAdError) {
                                            firstRewardedAd = null
                                            firstInterstitialAd = null
                                            isFirstAdLoading = false
                                            Log.e(TAG, "Test ad also failed: ${testError.message}")
                                        }
                                    }
                                )
                            }
                        }
                    )
                }
            }
        )
    }

    fun loadFastAd(context: Context) {
        if (fastInterstitialAd != null || fastRewardedAd != null || isFastAdLoading) return

        isFastAdLoading = true
        val adRequest = AdRequest.Builder().build()

        // 1. Try real Fast Interstitial
        InterstitialAd.load(
            context,
            FAST_AD_UNIT_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    fastInterstitialAd = ad
                    isFastAdLoading = false
                    Log.d(TAG, "Fast Real Ad (Interstitial) loaded!")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.w(TAG, "Fast Real Ad (Interstitial) failed. Trying Real Rewarded...")
                    RewardedAd.load(
                        context,
                        FAST_AD_UNIT_ID,
                        adRequest,
                        object : RewardedAdLoadCallback() {
                            override fun onAdLoaded(rewarded: RewardedAd) {
                                fastRewardedAd = rewarded
                                isFastAdLoading = false
                                Log.d(TAG, "Fast Real Ad (Rewarded) loaded!")
                            }

                            override fun onAdFailedToLoad(error: LoadAdError) {
                                Log.w(TAG, "Fast Real Ad failed. Falling back to Google Verified Test Interstitial...")
                                InterstitialAd.load(
                                    context,
                                    TEST_INTERSTITIAL_UNIT_ID,
                                    adRequest,
                                    object : InterstitialAdLoadCallback() {
                                        override fun onAdLoaded(testIntAd: InterstitialAd) {
                                            fastInterstitialAd = testIntAd
                                            isFastAdLoading = false
                                            Log.d(TAG, "Fast Test Interstitial Ad loaded and ready!")
                                        }

                                        override fun onAdFailedToLoad(testIntError: LoadAdError) {
                                            fastInterstitialAd = null
                                            fastRewardedAd = null
                                            isFastAdLoading = false
                                            Log.e(TAG, "Fast test ad failed: ${testIntError.message}")
                                        }
                                    }
                                )
                            }
                        }
                    )
                }
            }
        )
    }

    /**
     * Shows ad according to user flow:
     * 1. 1st time when 5 hearts are over: Shows FIRST_AD_UNIT_ID (15-20s ad)
     * 2. Subsequent times when user gets out: Shows FAST_AD_UNIT_ID (5s ad)
     */
    fun showGameOverAd(
        activity: Activity,
        onHeartAwarded: () -> Unit,
        onAdClosed: () -> Unit
    ) {
        if (!hasShownFirstDepletionAd) {
            // First time hearts are depleted: show 15-20s ad
            hasShownFirstDepletionAd = true
            showFirstAd(activity, onHeartAwarded, onAdClosed)
        } else {
            // Subsequent out: show 5s fast ad
            showFastAd(activity, onHeartAwarded, onAdClosed)
        }
    }

    private fun showFirstAd(
        activity: Activity,
        onHeartAwarded: () -> Unit,
        onAdClosed: () -> Unit
    ) {
        val rewAd = firstRewardedAd
        val intAd = firstInterstitialAd

        when {
            rewAd != null -> {
                rewAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        firstRewardedAd = null
                        loadFirstAd(activity.applicationContext)
                        onHeartAwarded()
                        onAdClosed()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        firstRewardedAd = null
                        loadFirstAd(activity.applicationContext)
                        onHeartAwarded()
                        onAdClosed()
                    }
                }
                rewAd.show(activity) {
                    onHeartAwarded()
                }
            }
            intAd != null -> {
                intAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        firstInterstitialAd = null
                        loadFirstAd(activity.applicationContext)
                        onHeartAwarded()
                        onAdClosed()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        firstInterstitialAd = null
                        loadFirstAd(activity.applicationContext)
                        onHeartAwarded()
                        onAdClosed()
                    }
                }
                intAd.show(activity)
            }
            else -> {
                Log.w(TAG, "First Ad was not yet in memory. Immediately attempting to show Fast Ad fallback.")
                if (fastInterstitialAd != null || fastRewardedAd != null) {
                    showFastAd(activity, onHeartAwarded, onAdClosed)
                } else {
                    // Preload immediately
                    loadFirstAd(activity.applicationContext)
                    onHeartAwarded()
                    onAdClosed()
                }
            }
        }
    }

    private fun showFastAd(
        activity: Activity,
        onHeartAwarded: () -> Unit,
        onAdClosed: () -> Unit
    ) {
        val intAd = fastInterstitialAd
        val rewAd = fastRewardedAd

        when {
            intAd != null -> {
                intAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        fastInterstitialAd = null
                        loadFastAd(activity.applicationContext)
                        onHeartAwarded()
                        onAdClosed()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        fastInterstitialAd = null
                        loadFastAd(activity.applicationContext)
                        onHeartAwarded()
                        onAdClosed()
                    }
                }
                intAd.show(activity)
            }
            rewAd != null -> {
                rewAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        fastRewardedAd = null
                        loadFastAd(activity.applicationContext)
                        onHeartAwarded()
                        onAdClosed()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        fastRewardedAd = null
                        loadFastAd(activity.applicationContext)
                        onHeartAwarded()
                        onAdClosed()
                    }
                }
                rewAd.show(activity) {
                    onHeartAwarded()
                }
            }
            else -> {
                Log.w(TAG, "Fast Ad was not ready yet. Reloading and granting heart.")
                loadFastAd(activity.applicationContext)
                onHeartAwarded()
                onAdClosed()
            }
        }
    }
}
