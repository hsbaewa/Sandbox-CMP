package kr.co.hs.sandbox.data.database.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kr.co.hs.sandbox.data.database.DatabaseDriverFactory
import kr.co.hs.sandbox.data.database.Mapper.toDomain
import kr.co.hs.sandbox.data.database.cache.Database
import kr.co.hs.sandbox.domain.entity.BoardEntity
import kr.co.hs.sandbox.domain.repository.BoardRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class DefaultBoardRepository : BoardRepository, KoinComponent {

    private val databaseDriverFactory: DatabaseDriverFactory = get()
    private val database by lazy { Database(databaseDriverFactory.createDriver()) }
    private val queries by lazy { database.databaseQueries }

    override fun flowOfCreateBoardItem(
        entity: BoardEntity
    ): Flow<BoardEntity> = flowOf(entity)
        .onEach {
            queries.insertBoard(
                boardTitle = entity.title,
                boardContents = entity.contents
            )
        }

    override fun flowOfGetAllBoard(): Flow<BoardEntity> = queries.selectAllBoard().executeAsList()
        .asFlow()
        .map { it.toDomain() }

    override fun flowOfGetBoard(title: String): Flow<BoardEntity> = flowOf(title)
        .map { queries.selectBoard(it).executeAsOne() }
        .map { it.toDomain() }
}