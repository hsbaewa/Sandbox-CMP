package kr.co.hs.sandbox.cmp.domain

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kr.co.hs.domain.usecase.NoErrorUseCase
import kr.co.hs.sandbox.cmp.UsingContext
import kr.co.hs.sandbox.domain.entity.BoardEntity
import kr.co.hs.sandbox.domain.usecase.CreateBoardUseCase
import kr.co.hs.sandbox.domain.usecase.GetBoardUseCase
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BoardDatabaseTest : KoinTest, UsingContext() {

    private val createBoard: CreateBoardUseCase by inject()
    private val getBoard: GetBoardUseCase by inject()

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalTestApi::class)
    @Test
    fun do_test_create_board_usecase() = runComposeUiTest {
        loadKoinModules(kr.co.hs.sandbox.data.database.di.repositoryModule)

        runTest {

            val title = "title_${Random.nextInt()}"
            val contents = "contents_${Random.nextInt()}"

            coroutineScope {

                createBoard(
                    object : BoardEntity {
                        override val title: String = title
                        override val contents: String = contents
                    }
                )
                    .map {
                        when (it) {
                            is NoErrorUseCase.Result.Exception -> throw it.t
                            is NoErrorUseCase.Result.Success -> it.data
                        }
                    }
                    .onEach {
                        assertEquals(title, it.title)
                    }
                    .flatMapConcat { getBoard(title) }
                    .map {
                        when (it) {
                            is NoErrorUseCase.Result.Exception -> throw it.t
                            is NoErrorUseCase.Result.Success -> it.data
                        }
                    }
                    .onEach {
                        assertEquals(title, it.title)
                    }
                    .catch { throw it }
                    .launchIn(this)
            }

            coroutineScope {

                val list = getBoard()
                    .map {
                        when (it) {
                            is NoErrorUseCase.Result.Exception -> throw it.t
                            is NoErrorUseCase.Result.Success -> it.data
                        }
                    }
                    .toList()

                assertTrue(list.isNotEmpty())
            }
        }
    }

}