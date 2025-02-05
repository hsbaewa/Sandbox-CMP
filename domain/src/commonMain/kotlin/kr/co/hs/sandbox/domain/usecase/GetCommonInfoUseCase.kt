package kr.co.hs.sandbox.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kr.co.hs.domain.usecase.NoErrorUseCase
import kr.co.hs.sandbox.domain.entity.CommonInfoEntity
import kr.co.hs.sandbox.domain.repository.CommonInfoRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class GetCommonInfoUseCase : NoErrorUseCase<Unit, CommonInfoEntity>(), KoinComponent {

    private val repository: CommonInfoRepository = get()

    operator fun invoke() = invoke(Unit)

    override fun invoke(
        param: Unit
    ): Flow<Result<CommonInfoEntity>> = repository
        .flowOfCommonInfo()
        .map { Result.Success(it) }
        .catch<Result<CommonInfoEntity>> { emit(Result.Exception(it)) }
}