package kr.co.hs.sandbox.cmp.domain

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runTest
import kr.co.hs.domain.usecase.NoErrorUseCase
import kr.co.hs.sandbox.domain.usecase.GetRemoteInfoUseCase
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class RemoteInfoTest : KoinTest {

    private val getRemoteInfo: GetRemoteInfoUseCase by inject()

    @Test
    fun do_test_get_remote_info_usecase_by_ktor() = runTest {
        startKoin {
            modules(
                kr.co.hs.sandbox.data.ktor.di.repositoryModule
            )
        }

        getRemoteInfo()
            .map {
                when (it) {
                    is NoErrorUseCase.Result.Exception -> throw it.t
                    is NoErrorUseCase.Result.Success -> it.data
                }
            }
            .onEach {
                assertEquals("PROCESS_AS_SNAPSHOT", it.text)
            }
            .onEach {
                assertNotEquals("", it.text)
            }
            .catch { throw it }
            .onCompletion {
                stopKoin()
            }
            .launchIn(this)
    }

    @Test
    fun do_test_remote_info_usecase_by_ktorfit() = runTest {
        startKoin {
            modules(
                kr.co.hs.sandbox.data.ktorfit.di.repositoryModule
            )
        }

        getRemoteInfo()
            .map {
                when (it) {
                    is NoErrorUseCase.Result.Exception -> throw it.t
                    is NoErrorUseCase.Result.Success -> it.data
                }
            }
            .onEach {
                assertEquals("PROCESS_AS_SNAPSHOT", it.text)
            }
            .onEach {
                assertNotEquals("", it.text)
            }
            .catch { throw it }
            .onCompletion {
                stopKoin()
            }
            .launchIn(this)
    }
}