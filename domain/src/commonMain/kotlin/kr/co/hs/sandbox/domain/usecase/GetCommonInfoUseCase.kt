package kr.co.hs.sandbox.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kr.co.hs.domain.usecase.NoErrorUseCase
import kr.co.hs.sandbox.domain.entity.CommonInfoEntity
import kr.co.hs.sandbox.domain.entity.impl.DefaultCommonInfoEntity

class GetCommonInfoUseCase : NoErrorUseCase<Unit, CommonInfoEntity>() {
    operator fun invoke() = invoke(Unit)

    override fun invoke(
        param: Unit
    ): Flow<Result<CommonInfoEntity>> = flowOf(DefaultCommonInfoEntity())
        .map { Result.Success(it) }
        .catch<Result<CommonInfoEntity>> { emit(Result.Exception(it)) }
}