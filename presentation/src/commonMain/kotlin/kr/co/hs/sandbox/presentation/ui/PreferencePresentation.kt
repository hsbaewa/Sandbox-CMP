package kr.co.hs.sandbox.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import kr.co.hs.sandbox.presentation.DefaultPresenter
import kr.co.hs.sandbox.presentation.Presentation
import kr.co.hs.sandbox.presentation.Presenter
import org.jetbrains.compose.resources.stringResource
import sandboxcmp.presentation.generated.resources.Res
import sandboxcmp.presentation.generated.resources.main_click_me_button

@Composable
fun PreferencePresentation(
    modifier: Modifier = Modifier,
    preferenceSample: Presentation.PreferenceSample,
    presenter: Presenter = DefaultPresenter(rememberNavController()),
    preferenceViewModel: PreferenceViewModel = viewModel { PreferenceViewModel() }
) {
    val clickCount by preferenceViewModel.clickCount.collectAsState()

    Column(
        modifier = modifier
    ) {
        Text(
            text = "text : ${preferenceSample::class.simpleName}"
        )

        Button(
            onClick = {
                preferenceViewModel.flowOfUpCountButtonClick()
            },
            content = {
                Text(
                    text = stringResource(Res.string.main_click_me_button) + clickCount.toString()
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
                    param(FirebaseAnalyticsParam.ITEM_NAME, "PreferencePresentation")
                }
            }
            .catch {}
            .launchIn(this)
    }
}