package kr.co.hs.sandbox.cmp.compose

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import kr.co.hs.sandbox.cmp.App
import kr.co.hs.sandbox.cmp.UsingContext
import kotlin.test.Test

class MainAppTest : UsingContext() {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun do_test_launch_main_app() = runComposeUiTest {
        setContent {
            initPreviewContextConfiguration()
            App()
        }

        onNodeWithText("only english").assertExists()
    }
}