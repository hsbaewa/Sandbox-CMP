package kr.co.hs.sandbox.cmp.domain

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runTest
import kr.co.hs.domain.usecase.NoErrorUseCase
import kr.co.hs.sandbox.cmp.UsingContext
import kr.co.hs.sandbox.domain.usecase.GetButtonClickCountUseCase
import kr.co.hs.sandbox.domain.usecase.UpCountButtonClickUseCase
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals

class ButtonClickCountTest : KoinTest, UsingContext() {

    private val upCount: UpCountButtonClickUseCase by inject()
    private val getCount: GetButtonClickCountUseCase by inject()

    @OptIn(ExperimentalTestApi::class, ExperimentalCoroutinesApi::class)
    @Test
    fun do_test_up_count_usecase() = runComposeUiTest {
        loadKoinModules(
            listOf(
                kr.co.hs.sandbox.data.preference.di.repositoryModule
            )
        )

        runTest {

            coroutineScope {
                flow { emit(getCount().first()) }
                    .map {
                        when (it) {
                            is NoErrorUseCase.Result.Exception -> throw it.t
                            is NoErrorUseCase.Result.Success -> it.data
                        }
                    }
                    .onEach {
                        assertEquals(0, it)
                    }
                    .catch { throw it }
                    .launchIn(this)
            }

            coroutineScope {
                upCount()
                    .map {
                        when (it) {
                            is NoErrorUseCase.Result.Exception -> throw it.t
                            is NoErrorUseCase.Result.Success -> it.data
                        }
                    }
                    .onEach { if (!it) throw Exception("failed up count") }
                    .flatMapConcat { flow { emit(getCount().first()) } }
                    .map {
                        when (it) {
                            is NoErrorUseCase.Result.Exception -> throw it.t
                            is NoErrorUseCase.Result.Success -> it.data
                        }
                    }
                    .onEach {
                        assertEquals(1, it)
                    }
                    .catch { throw it }
                    .launchIn(this)
            }

        }
    }
}