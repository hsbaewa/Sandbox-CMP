package kr.co.hs.sandbox.cmp

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runTest
import kr.co.hs.domain.usecase.NoErrorUseCase
import kr.co.hs.sandbox.domain.usecase.GetCommonInfoUseCase
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CommonInfoTest : KoinTest {

    private val getCommonInfo: GetCommonInfoUseCase by inject()

    @Test
    fun do_test_get_common_info_usecase() = runTest {
        startKoin {
            modules(
                kr.co.hs.sandbox.data.di.repositoryModule
            )
        }

        getCommonInfo()
            .map {
                when (it) {
                    is NoErrorUseCase.Result.Exception -> throw it.t
                    is NoErrorUseCase.Result.Success -> it.data
                }
            }
            .onEach {
                assertEquals("this is common entity text", it.text)
            }
            .onEach {
                assertNotNull("", it.text)
            }
            .catch { throw it }
            .onCompletion {
                stopKoin()
            }
            .launchIn(this)
    }
}