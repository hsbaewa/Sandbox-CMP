package kr.co.hs.sandbox.cmp

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController {
    try {
        startKoin {
            modules(
                kr.co.hs.sandbox.data.di.repositoryModule,
                kr.co.hs.sandbox.data.ktorfit.di.repositoryModule,
                kr.co.hs.sandbox.data.preference.di.repositoryModule
            )
        }
    } catch (_: Exception) {
        // A Koin Application has already been started
        /**
        반복해서 호출되는 경우 iOS 단말에서 오류(A Koin Application has already been started)가 발생하여 예외 무시하도록 수정.
         */
    }
    App()
}