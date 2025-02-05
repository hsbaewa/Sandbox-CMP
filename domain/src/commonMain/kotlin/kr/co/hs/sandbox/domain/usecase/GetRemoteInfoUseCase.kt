package kr.co.hs.sandbox.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kr.co.hs.domain.usecase.NoErrorUseCase
import kr.co.hs.sandbox.domain.entity.RemoteInfoEntity
import kr.co.hs.sandbox.domain.repository.RemoteInfoRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class GetRemoteInfoUseCase : NoErrorUseCase<Unit, RemoteInfoEntity>(), KoinComponent {

    private val repository: RemoteInfoRepository = get()

    operator fun invoke() = invoke(Unit)

    override fun invoke(
        param: Unit
    ): Flow<Result<RemoteInfoEntity>> = repository
        .flowOfRemoteInfo()
        .map { Result.Success(it) }
        .catch<Result<RemoteInfoEntity>> { emit(Result.Exception(it)) }
}