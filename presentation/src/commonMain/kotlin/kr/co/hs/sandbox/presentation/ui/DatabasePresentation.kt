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
import kotlin.random.Random

@Composable
fun DatabasePresentation(
    modifier: Modifier = Modifier,
    databaseSample: Presentation.DatabaseSample,
    presenter: Presenter = DefaultPresenter(rememberNavController()),
    boardViewModel: BoardViewModel = viewModel { BoardViewModel() },
) {
    val allBoards by boardViewModel.boards.collectAsState()

    Column(
        modifier = modifier
    ) {
        Text(
            text = "text : ${databaseSample::class.simpleName}"
        )
        Text("board count : ${allBoards.size}")
        Button(
            onClick = {
                boardViewModel.flowOfCreate(
                    Random.nextInt().toString(),
                    Random.nextInt().toString()
                )
            },
            content = {
                Text(
                    stringResource(Res.string.main_click_me_button) + allBoards.size.toString()
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
                    param(FirebaseAnalyticsParam.ITEM_NAME, "DatabasePresentation")
                }
            }
            .catch {}
            .launchIn(this)
    }
}