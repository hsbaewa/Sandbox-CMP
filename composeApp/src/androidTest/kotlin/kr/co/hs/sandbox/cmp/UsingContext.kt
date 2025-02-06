package kr.co.hs.sandbox.cmp

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable

/**
 * Robolectric for instrumentation
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual open class UsingContext actual constructor() {
    @SuppressLint("ComposableNaming")
    @Composable
    actual fun initPreviewContextConfiguration() = Unit
}