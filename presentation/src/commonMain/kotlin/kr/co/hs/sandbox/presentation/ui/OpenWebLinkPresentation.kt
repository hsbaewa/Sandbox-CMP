package kr.co.hs.sandbox.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
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
import kr.co.hs.sandbox.presentation.DefaultPresenter
import kr.co.hs.sandbox.presentation.Presentation
import kr.co.hs.sandbox.presentation.Presenter
import kr.co.hs.sandbox.presentation.getUriLauncher

@Composable
fun OpenWebLinkPresentation(
    modifier: Modifier = Modifier,
    openWebLinkSample: Presentation.OpenWebLinkSample,
    presenter: Presenter = DefaultPresenter(rememberNavController())
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "text : ${openWebLinkSample::class.simpleName}"
        )

        Button(
            onClick = {
                getUriLauncher().launchWebUrl(openWebLinkSample.url)
            },
            content = {
                Text(
                    text = openWebLinkSample.url
                )
            }
        )

        Button(
            onClick = {
                presenter.popToHome()
            },
            content = {
                Text(text = "home")
            }
        )
    }

    LaunchedEffect(Unit) {
        flow { emit(Firebase.analytics) }
            .onEach {
                it.logEvent(FirebaseAnalyticsEvents.VIEW_ITEM) {
                    param(FirebaseAnalyticsParam.ITEM_NAME, "OpenWebLinkSample")
                }
            }
            .catch {}
            .launchIn(this)
    }
}