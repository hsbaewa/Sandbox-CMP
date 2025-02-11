package kr.co.hs.sandbox.presentation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.co.hs.sandbox.presentation.ui.DatabasePresentation
import kr.co.hs.sandbox.presentation.ui.DateTimePresentation
import kr.co.hs.sandbox.presentation.ui.MainPresentation
import kr.co.hs.sandbox.presentation.ui.OpenWebLinkPresentation
import kr.co.hs.sandbox.presentation.ui.PreferencePresentation

@Composable
fun rememberPresenter(): Presenter {
    val navHostController = rememberNavController()
    return remember { DefaultPresenter(navHostController) }
}

class DefaultPresenter(
    private val navController: NavHostController
) : Presenter {
    override fun flowOfCurrentPresentation(): Flow<Presentation> = navController
        .currentBackStackEntryFlow
        .map {
            val routeClassName = it.destination.route?.let { route ->
                route.indexOf('/').takeIf { idx -> idx >= 0 }
                    ?.let { idx -> route.substring(0 until idx) }
                    ?: route
            }
            when (routeClassName) {
                Presentation.Main::class.qualifiedName -> it.toRoute<Presentation.Main>()
                Presentation.DateTimeSample::class.qualifiedName -> it.toRoute<Presentation.DateTimeSample>()
                Presentation.DatabaseSample::class.qualifiedName -> it.toRoute<Presentation.DatabaseSample>()
                Presentation.PreferenceSample::class.qualifiedName -> it.toRoute<Presentation.PreferenceSample>()
                Presentation.OpenWebLinkSample::class.qualifiedName -> it.toRoute<Presentation.OpenWebLinkSample>()
                else -> throw Exception("unknown destination")
            }
        }

    override fun previousPresentation(): Presentation? =
        navController.previousBackStackEntry?.toRoute<Presentation>()

    override fun navigate(presentation: Presentation) = navController.navigate(presentation)

    override fun popBackStack(): Boolean = navController.popBackStack()

    override fun popToHome(): Boolean =
        navController.popBackStack(Presentation.Main, inclusive = false)

    @Composable
    override fun NavHost(
        modifier: Modifier
    ) = androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = Presentation.Main,
        modifier = modifier,
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
        popExitTransition = { fadeOut(tween(300)) },
        builder = {
            composable<Presentation.Main> {
                MainPresentation(
                    main = it.toRoute(),
                    presenter = this@DefaultPresenter
                )
            }
            composable<Presentation.DateTimeSample> {
                DateTimePresentation(
                    dateTimeSample = it.toRoute(),
                    presenter = this@DefaultPresenter
                )
            }
            composable<Presentation.PreferenceSample> {
                PreferencePresentation(
                    preferenceSample = it.toRoute(),
                    presenter = this@DefaultPresenter
                )
            }
            composable<Presentation.DatabaseSample> {
                DatabasePresentation(
                    databaseSample = it.toRoute(),
                    presenter = this@DefaultPresenter
                )
            }
            composable<Presentation.OpenWebLinkSample> {
                OpenWebLinkPresentation(
                    openWebLinkSample = it.toRoute(),
                    presenter = this@DefaultPresenter
                )
            }
        }
    )
}