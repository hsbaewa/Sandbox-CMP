package kr.co.hs.sandbox.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kr.co.hs.domain.usecase.NoErrorUseCase
import kr.co.hs.sandbox.domain.entity.BoardEntity
import kr.co.hs.sandbox.domain.repository.BoardRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class CreateBoardUseCase : NoErrorUseCase<BoardEntity, BoardEntity>(), KoinComponent {

    private val repository: BoardRepository = get()

    override fun invoke(
        param: BoardEntity
    ): Flow<Result<BoardEntity>> = repository
        .flowOfCreateBoardItem(param)
        .map { Result.Success(it) }
        .catch<Result<BoardEntity>> { emit(Result.Exception(it)) }
}