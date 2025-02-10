package kr.co.hs.sandbox.cmp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.FirebaseAnalyticsEvents
import dev.gitlive.firebase.analytics.FirebaseAnalyticsParam
import dev.gitlive.firebase.analytics.analytics
import dev.gitlive.firebase.analytics.logEvent
import dev.gitlive.firebase.crashlytics.crashlytics
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kr.co.hs.sandbox.cmp.ui.BoardViewModel
import kr.co.hs.sandbox.cmp.ui.theme.AppTheme
import kr.co.hs.sandbox.cmp.ui.CommonInfoViewModel
import kr.co.hs.sandbox.cmp.ui.PlatformInfoViewModel
import kr.co.hs.sandbox.cmp.ui.PreferenceViewModel
import kr.co.hs.sandbox.presentation.getUriLauncher
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.context.loadKoinModules

import sandboxcmp.composeapp.generated.resources.Res
import sandboxcmp.composeapp.generated.resources.locale_flag
import sandboxcmp.composeapp.generated.resources.main_click_me_button
import sandboxcmp.composeapp.generated.resources.main_title
import sandboxcmp.composeapp.generated.resources.main_untranslatable
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class, ExperimentalEncodingApi::class)
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

    LaunchedEffect(Unit) {
        flow { emit(Firebase.analytics) }
            .onEach {
                it.logEvent(FirebaseAnalyticsEvents.APP_OPEN) {
                    param(FirebaseAnalyticsParam.ITEM_NAME, "Main Composable Open")
                }
            }
            .catch {}
            .launchIn(this)
    }

    AppTheme {

        val navHostController = rememberNavController()
        val coroutineScope = rememberCoroutineScope()
        var hasBackRoute by remember { mutableStateOf(false) }
        var currentRoute by remember { mutableStateOf<String?>(null) }
        DisposableEffect(Unit) {
            val job = navHostController
                .currentBackStackEntryFlow
                .onEach {
                    hasBackRoute = navHostController.previousBackStackEntry != null
                    currentRoute = it.destination.route
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
                            CommonNavigationIconButton(onClick = { navHostController.popBackStack() })
                        }
                    }
                )
            },
            contentWindowInsets = WindowInsets.systemBars,
            content = {
                NavHost(
                    navController = navHostController,
                    startDestination = "a",
                    modifier = Modifier.padding(it),
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> -fullWidth.div(4) },
                            animationSpec = tween(300)
                        ) + fadeIn(tween(300))
                    },
                    exitTransition = { fadeOut(tween(300)) },
                    popEnterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> -fullWidth.div(4) },
                            animationSpec = tween(300)
                        ) + fadeIn(tween(300))
                    },
                    popExitTransition = { fadeOut(tween(300)) }
                ) {
                    composable(
                        route = "a"
                    ) {
                        Content(
                            onClickOther = {
                                val stringPathParameter =
                                    Base64.encode("path/of/parameter".encodeToByteArray())
                                val intParameter = Random.nextInt()
                                navHostController.navigate("b/$stringPathParameter/$intParameter")
                            }
                        )
                    }

                    composable(
                        route = "b/{stringParameter}/{intParameter}",
                        arguments = listOf(
                            navArgument("stringParameter") { type = NavType.StringType },
                            navArgument("intParameter") { type = NavType.IntType }
                        )
                    ) { navBackStackEntry ->
                        val stringParameter = navBackStackEntry.arguments
                            ?.getString("stringParameter")
                            ?.let { p -> Base64.decode(p) }
                            ?.decodeToString()
                        val intParameter = navBackStackEntry.arguments
                            ?.getInt("intParameter")

                        ContentOther(
                            stringParameter = stringParameter,
                            intParameter = intParameter
                        )
                    }
                }


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

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    platformInfoViewModel: PlatformInfoViewModel = viewModel { PlatformInfoViewModel() },
    commonInfoViewModel: CommonInfoViewModel = viewModel { CommonInfoViewModel() },
    preferenceViewModel: PreferenceViewModel = viewModel { PreferenceViewModel() },
    boardViewModel: BoardViewModel = viewModel { BoardViewModel() },
    onClickOther: () -> Unit = {}
) {
    var showContent by remember { mutableStateOf(false) }

    LaunchedEffect(showContent) {
        if (showContent) {
            flow { emit(Firebase.analytics) }
                .onEach {
                    it.logEvent(FirebaseAnalyticsEvents.VIEW_ITEM) {
                        param(FirebaseAnalyticsParam.ITEM_NAME, "Click Button!!")
                    }
                }
                .catch {}
                .launchIn(this)

            getUriLauncher().launchWebUrl("https://google.com")
        }
    }

    val clickCount by preferenceViewModel.clickCount.collectAsState()
    val allBoards by boardViewModel.boards.collectAsState()

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(stringResource(Res.string.main_untranslatable))
        Text("board count : ${allBoards.size}")


        val instant = Clock.System.now()
        Text("instant = ${instant.toEpochMilliseconds()}")

        val timeZone = TimeZone.currentSystemDefault()
        val localDateTime = instant.toLocalDateTime(timeZone)
        Text("year = ${localDateTime.year}")
        Text("month = ${localDateTime.monthNumber}")

        Text("millis = ${instant.toEpochMilliseconds()}")

        Text("toInstant = ${localDateTime.toInstant(timeZone)}")

        val format = LocalDateTime.Format {
            year()
            chars("-")
            monthNumber()
            chars("-")
            dayOfMonth()
            chars(" ")
            hour()
            chars(":")
            minute()
            chars(":")
            second()
        }

        Text("dateFormat = ${localDateTime.format(format)}")


        Button(
            onClick = {
                showContent = !showContent
                preferenceViewModel.flowOfUpCountButtonClick()
                boardViewModel.flowOfCreate(
                    Random.nextInt().toString(),
                    Random.nextInt().toString()
                )
            }
        ) {
            Text(stringResource(Res.string.main_click_me_button) + " $clickCount")
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
                onClickOther.invoke()
            }
        ) {
            Text("navigate other")
        }
    }
}

@Composable
private fun ContentOther(
    stringParameter: String? = null,
    intParameter: Int? = null
) {
    Column {
        Text("stringParameter : $stringParameter")
        Text("intParameter : $intParameter")
    }
}