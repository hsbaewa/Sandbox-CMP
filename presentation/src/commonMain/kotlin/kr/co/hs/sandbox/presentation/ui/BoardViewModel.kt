package kr.co.hs.sandbox.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kr.co.hs.domain.usecase.NoErrorUseCase
import kr.co.hs.sandbox.domain.entity.BoardEntity
import kr.co.hs.sandbox.domain.usecase.CreateBoardUseCase
import kr.co.hs.sandbox.domain.usecase.GetBoardUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class BoardViewModel : ViewModel(), KoinComponent {

    private val getBoard: GetBoardUseCase = get()
    private val createBoard: CreateBoardUseCase = get()

    private val _boards = MutableStateFlow<List<BoardEntity>>(emptyList())
    val boards: StateFlow<List<BoardEntity>> = _boards.asStateFlow()

    init {
        flow {
            val list = getBoard()
                .map {
                    when (it) {
                        is NoErrorUseCase.Result.Exception -> throw it.t
                        is NoErrorUseCase.Result.Success -> it.data
                    }
                }
                .toList()
            emit(list)
        }
            .flowOn(Dispatchers.IO)
            .onEach { _boards.value = it }
            .launchIn(viewModelScope)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun flowOfCreate(
        title: String,
        contents: String
    ) = createBoard(
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
        .flatMapConcat { getBoard(it.title) }
        .map {
            when (it) {
                is NoErrorUseCase.Result.Exception -> throw it.t
                is NoErrorUseCase.Result.Success -> it.data
            }
        }
        .flowOn(Dispatchers.IO)
        .onEach {
            val list = _boards.value.toMutableList().apply { add(0, it) }
            _boards.value = list
        }
        .launchIn(viewModelScope)
}