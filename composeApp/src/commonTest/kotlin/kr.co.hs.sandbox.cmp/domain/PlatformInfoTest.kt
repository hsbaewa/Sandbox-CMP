package kr.co.hs.sandbox.cmp.domain

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runTest
import kr.co.hs.domain.usecase.NoErrorUseCase
import kr.co.hs.sandbox.domain.usecase.GetPlatformInfoUseCase
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class PlatformInfoTest : KoinTest {

    private val getPlatformInfo: GetPlatformInfoUseCase by inject()

    @Test
    fun do_test_get_platform_info_usecase() = runTest {
        startKoin {
            modules(
                kr.co.hs.sandbox.data.di.repositoryModule
            )
        }

        getPlatformInfo()
            .map {
                when (it) {
                    is NoErrorUseCase.Result.Exception -> throw it.t
                    is NoErrorUseCase.Result.Success -> it.data
                }
            }
            .onEach {
                assertTrue(it.os.startsWith("Android"))
            }
            .onEach {
                assertNotEquals("", it.os)
            }
            .catch { throw it }
            .onCompletion {
                stopKoin()
            }
            .launchIn(this)
    }
}