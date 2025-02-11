package kr.co.hs.sandbox.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.FirebaseAnalyticsEvents
import dev.gitlive.firebase.analytics.FirebaseAnalyticsParam
import dev.gitlive.firebase.analytics.analytics
import dev.gitlive.firebase.analytics.logEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.Clock
import kr.co.hs.sandbox.presentation.DefaultPresenter
import kr.co.hs.sandbox.presentation.Presentation
import kr.co.hs.sandbox.presentation.Presenter
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import sandboxcmp.presentation.generated.resources.Res
import sandboxcmp.presentation.generated.resources.locale_flag
import sandboxcmp.presentation.generated.resources.main_click_me_button

@Composable
fun MainPresentation(
    modifier: Modifier = Modifier,
    main: Presentation.Main,
    presenter: Presenter = DefaultPresenter(rememberNavController()),
    platformInfoViewModel: PlatformInfoViewModel = viewModel { PlatformInfoViewModel() },
    commonInfoViewModel: CommonInfoViewModel = viewModel { CommonInfoViewModel() }
) {
    println(main)
    var showContent by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        Text(
            text = "text : ${main::class.simpleName}"
        )
        Button(
            onClick = {
                showContent = !showContent
            }
        ) {
            Text(stringResource(Res.string.main_click_me_button))
        }
        AnimatedVisibility(showContent) {
            val os by platformInfoViewModel.os.collectAsState()
            val commonText by commonInfoViewModel.text.collectAsState()
            val remoteText by commonInfoViewModel.remoteText.collectAsState()

            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painterResource(Res.drawable.locale_flag), null)
                os?.let { os -> Text("Compose: $os") }
                commonText?.let { t -> Text("Common Text: $t") }
                remoteText?.let { t -> Text("Remote Text: $t") }
            }
        }

        Button(
            onClick = {
                presenter.navigate(
                    Presentation.DateTimeSample(
                        millis = Clock.System.now().toEpochMilliseconds()
                    )
                )
            },
            content = {
                Text(text = "SampleDateTime")
            }
        )

        Button(
            onClick = {
                presenter.navigate(Presentation.PreferenceSample)
            },
            content = {
                Text(text = "PreferenceSample")
            }
        )

        Button(
            onClick = {
                presenter.navigate(Presentation.DatabaseSample)
            },
            content = {
                Text(text = "DatabaseSample")
            }
        )

        Button(
            onClick = {
                presenter.navigate(
                    Presentation.OpenWebLinkSample(
                        url = "https://google.com"
                    )
                )
            },
            content = {
                Text(text = "OpenWebLinkSample")
            }
        )
    }

    LaunchedEffect(Unit) {
        flow { emit(Firebase.analytics) }
            .onEach {
                it.logEvent(FirebaseAnalyticsEvents.VIEW_ITEM) {
                    param(FirebaseAnalyticsParam.ITEM_NAME, "MainPresentation")
                }
            }
            .catch {}
            .launchIn(this)
    }
}