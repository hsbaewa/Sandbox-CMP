package kr.co.hs.sandbox.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.Flow

interface Presenter {
    fun flowOfCurrentPresentation(): Flow<Presentation>
    fun previousPresentation(): Presentation?
    fun navigate(presentation: Presentation)
    fun popBackStack(): Boolean
    fun popToHome(): Boolean

    @Composable
    fun NavHost(modifier: Modifier)
}