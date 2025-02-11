package kr.co.hs.sandbox.cmp

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.analytics
import dev.gitlive.firebase.crashlytics.crashlytics
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.co.hs.sandbox.cmp.ui.theme.AppTheme
import kr.co.hs.sandbox.presentation.Presentation
import kr.co.hs.sandbox.presentation.rememberPresenter
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.context.loadKoinModules
import sandboxcmp.composeapp.generated.resources.Res
import sandboxcmp.composeapp.generated.resources.main_title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {

    loadKoinModules(
        listOf(
            kr.co.hs.sandbox.data.di.repositoryModule,
            kr.co.hs.sandbox.data.ktorfit.di.repositoryModule,
            kr.co.hs.sandbox.data.preference.di.repositoryModule,
            kr.co.hs.sandbox.data.database.di.repositoryModule
        )
    )

    runCatching { Firebase.analytics.setAnalyticsCollectionEnabled(true) }.getOrNull()
    runCatching { Firebase.crashlytics.setCrashlyticsCollectionEnabled(true) }.getOrNull()

    AppTheme {

        val presenter = rememberPresenter()
        val coroutineScope = rememberCoroutineScope()
        var hasBackRoute by remember { mutableStateOf(false) }
        var currentRoute by remember { mutableStateOf<String?>(null) }
        DisposableEffect(Unit) {
            val job = presenter
                .flowOfCurrentPresentation()
                .onEach {
                    currentRoute = it::class.simpleName
                    hasBackRoute = it !is Presentation.Main
                }
                .launchIn(coroutineScope)
            onDispose { job.cancel() }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(Res.string.main_title) + " : $currentRoute"
                        )
                    },
                    navigationIcon = {
                        if (hasBackRoute) {
                            CommonNavigationIconButton(onClick = { presenter.popBackStack() })
                        }
                    }
                )
            },
            contentWindowInsets = WindowInsets.systemBars,
            content = {
                presenter.NavHost(Modifier.padding(it))
            }
        )

    }
}

@Composable
fun CommonNavigationIconButton(onClick: (() -> Unit)? = null) {
    IconButton(
        onClick = {
            onClick?.invoke()
        },
        content = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = ""
            )
        }
    )
}