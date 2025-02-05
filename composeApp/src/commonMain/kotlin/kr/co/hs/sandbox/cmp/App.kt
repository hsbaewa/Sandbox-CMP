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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kr.co.hs.domain.usecase.NoErrorUseCase
import kr.co.hs.sandbox.cmp.ui.AppTheme
import kr.co.hs.sandbox.domain.usecase.GetCommonInfoUseCase
import kr.co.hs.sandbox.domain.usecase.GetPlatformInfoUseCase
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
    modifier: Modifier = Modifier
) {
    var showContent by remember { mutableStateOf(false) }
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(stringResource(Res.string.main_untranslatable))
        Button(onClick = { showContent = !showContent }) {
            Text(stringResource(Res.string.main_click_me_button))
        }
        AnimatedVisibility(showContent) {
            var os by remember { mutableStateOf<String?>(null) }
            var commonText by remember { mutableStateOf<String?>(null) }

            LaunchedEffect(Unit) {
                val getPlatformInfo = GetPlatformInfoUseCase()
                getPlatformInfo()
                    .flowOn(Dispatchers.IO)
                    .map {
                        when (it) {
                            is NoErrorUseCase.Result.Exception -> throw it.t
                            is NoErrorUseCase.Result.Success -> it.data
                        }
                    }
                    .onEach { os = it.os }
                    .catch { os = it.message }
                    .launchIn(this)

                val getCommonInfo = GetCommonInfoUseCase()
                getCommonInfo()
                    .flowOn(Dispatchers.IO)
                    .map {
                        when (it) {
                            is NoErrorUseCase.Result.Exception -> throw it.t
                            is NoErrorUseCase.Result.Success -> it.data
                        }
                    }
                    .onEach { commonText = it.text }
                    .catch { commonText = it.message }
                    .launchIn(this)
            }

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