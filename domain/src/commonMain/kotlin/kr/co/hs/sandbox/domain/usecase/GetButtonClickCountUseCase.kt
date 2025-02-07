package kr.co.hs.sandbox.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kr.co.hs.domain.usecase.NoErrorUseCase
import kr.co.hs.sandbox.domain.repository.PreferenceRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class GetButtonClickCountUseCase : NoErrorUseCase<Unit, Int>(), KoinComponent {

    private val repository: PreferenceRepository = get()

    operator fun invoke() = invoke(Unit)

    override fun invoke(param: Unit): Flow<Result<Int>> = repository
        .flowOfButtonClickCount()
        .map { Result.Success(it) }
        .catch<Result<Int>> { emit(Result.Exception(it)) }
}