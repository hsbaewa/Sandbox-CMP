package kr.co.hs.sandbox.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kr.co.hs.domain.usecase.NoErrorUseCase
import kr.co.hs.sandbox.domain.entity.PlatformInfoEntity
import kr.co.hs.sandbox.domain.repository.PlatformInfoRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class GetPlatformInfoUseCase : NoErrorUseCase<Unit, PlatformInfoEntity>(), KoinComponent {

    private val repository: PlatformInfoRepository = get()

    operator fun invoke() = invoke(Unit)

    override fun invoke(
        param: Unit
    ): Flow<Result<PlatformInfoEntity>> = repository
        .flowOfPlatformInfo()
        .map { Result.Success(it) }
        .catch<Result<PlatformInfoEntity>> { emit(Result.Exception(it)) }
}