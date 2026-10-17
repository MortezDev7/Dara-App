package com.x7.dara

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.x7.dara.ui.screens.DaraApp
import com.x7.dara.ui.theme.DaraTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var contentReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        val startTime = SystemClock.uptimeMillis()
        val splashScreen = installSplashScreen()

        splashScreen.setKeepOnScreenCondition {
            !contentReady || SystemClock.uptimeMillis() - startTime < 1500
        }

        splashScreen.setOnExitAnimationListener { splashProvider ->
            val icon = splashProvider.iconView
            val screenWidth = icon.resources.displayMetrics.widthPixels.toFloat()

            val riseDistance = screenWidth * 0.071f
            val fallDistance = screenWidth * 0.76f

            val up = ObjectAnimator.ofFloat(
                icon, View.TRANSLATION_Y, 0f, -riseDistance
            ).apply {
                duration = 200
                interpolator = DecelerateInterpolator()
            }

            val down = ObjectAnimator.ofFloat(
                icon, View.TRANSLATION_Y, -riseDistance, fallDistance - riseDistance
            ).apply {
                duration = 320
                interpolator = AccelerateInterpolator(1.5f)
            }

            val fade = ObjectAnimator.ofFloat(icon, View.ALPHA, 1f, 0f).apply {
                duration = 130
                startDelay = 190
            }

            val fall = AnimatorSet().apply {
                playTogether(down, fade)
            }

            AnimatorSet().apply {
                playSequentially(up, fall)
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        splashProvider.remove()
                    }
                })
                start()
            }
        }

        super.onCreate(savedInstanceState)

        setContent {
            DaraTheme {
                DaraApp()
                SideEffect { contentReady = true }
            }
        }
    }
}