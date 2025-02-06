package kr.co.hs.sandbox.cmp

import androidx.compose.runtime.Composable

/**
 * Robolectric for instrumentation
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual open class UsingContext actual constructor() {
    @Composable
    actual fun initPreviewContextConfiguration() = Unit
}