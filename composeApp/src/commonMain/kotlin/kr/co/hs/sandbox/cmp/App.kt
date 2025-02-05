package kr.co.hs.sandbox.cmp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import kr.co.hs.sandbox.cmp.ui.theme.AppTheme
import kr.co.hs.sandbox.cmp.ui.CommonInfoViewModel
import kr.co.hs.sandbox.cmp.ui.PlatformInfoViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import sandboxcmp.composeapp.generated.resources.Res
import sandboxcmp.composeapp.generated.resources.locale_flag
import sandboxcmp.composeapp.generated.resources.main_click_me_button
import sandboxcmp.composeapp.generated.resources.main_title
import sandboxcmp.composeapp.generated.resources.main_untranslatable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    AppTheme {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(Res.string.main_title)
                        )
                    }
                )
            },
            contentWindowInsets = WindowInsets.systemBars,
            content = {
                Content(
                    modifier = Modifier.padding(it)
                )
            }
        )

    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    platformInfoViewModel: PlatformInfoViewModel = viewModel { PlatformInfoViewModel() },
    commonInfoViewModel: CommonInfoViewModel = viewModel { CommonInfoViewModel() }
) {
    var showContent by remember { mutableStateOf(false) }
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(stringResource(Res.string.main_untranslatable))
        Button(onClick = { showContent = !showContent }) {
            Text(stringResource(Res.string.main_click_me_button))
        }
        AnimatedVisibility(showContent) {
            val os by platformInfoViewModel.os.collectAsState()
            val commonText by commonInfoViewModel.text.collectAsState()

            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painterResource(Res.drawable.locale_flag), null)
                os?.let { os -> Text("Compose: $os") }
                commonText?.let { t -> Text("Common Text: $t") }
            }
        }
    }
}