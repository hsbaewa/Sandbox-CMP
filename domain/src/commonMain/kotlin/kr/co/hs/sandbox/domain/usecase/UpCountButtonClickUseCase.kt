package kr.co.hs.sandbox.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kr.co.hs.domain.usecase.NoErrorUseCase
import kr.co.hs.sandbox.domain.repository.PreferenceRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class UpCountButtonClickUseCase : NoErrorUseCase<Unit, Boolean>(), KoinComponent {

    private val repository: PreferenceRepository = get()

    operator fun invoke() = invoke(Unit)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun invoke(param: Unit): Flow<Result<Boolean>> =
        flow { emit(repository.flowOfButtonClickCount().first()) }
            .flatMapConcat { repository.flowOfButtonClickCount(it + 1) }
            .map { Result.Success(it) }
            .catch<Result<Boolean>> { emit(Result.Exception(it)) }
}