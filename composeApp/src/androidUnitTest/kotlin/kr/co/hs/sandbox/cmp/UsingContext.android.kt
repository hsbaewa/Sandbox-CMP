package kr.co.hs.sandbox.cmp

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalInspectionMode
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.PreviewContextConfigurationEffect
import org.junit.jupiter.api.extension.ExtendWith
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog
import tech.apter.junit.jupiter.robolectric.RobolectricExtension
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

/**
 * Robolectric for instrumentation
 */
@ExtendWith(RobolectricExtension::class)
@Config(sdk = [Config.OLDEST_SDK, Build.VERSION_CODES.UPSIDE_DOWN_CAKE])
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual open class UsingContext {

    @BeforeTest
    open fun setUp() {
        ShadowLog.stream = System.out
//        startKoin {
//            androidLogger()
//            androidContext(RuntimeEnvironment.getApplication())
//        }
    }

    @SuppressLint("ComposableNaming")
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    actual fun initPreviewContextConfiguration() {
        CompositionLocalProvider(LocalInspectionMode provides true) {
            PreviewContextConfigurationEffect()
        }
    }

    @AfterTest
    open fun setDown() {
//        stopKoin()
    }
}