package kr.co.hs.sandbox.cmp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kr.co.hs.domain.usecase.NoErrorUseCase
import kr.co.hs.sandbox.domain.usecase.GetButtonClickCountUseCase
import kr.co.hs.sandbox.domain.usecase.UpCountButtonClickUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class PreferenceViewModel : ViewModel(), KoinComponent {

    private val upCountButtonClick: UpCountButtonClickUseCase = get()
    private val getButtonClickCount: GetButtonClickCountUseCase = get()

    private val _clickCount = MutableStateFlow(0)
    val clickCount: StateFlow<Int> = _clickCount.asStateFlow()

    init {
        getButtonClickCount()
            .map {
                when (it) {
                    is NoErrorUseCase.Result.Exception -> throw it.t
                    is NoErrorUseCase.Result.Success -> it.data
                }
            }
            .flowOn(Dispatchers.IO)
            .onEach { _clickCount.value = it }
            .launchIn(viewModelScope)
    }

    fun flowOfUpCountButtonClick() = upCountButtonClick()
        .flowOn(Dispatchers.IO)
        .launchIn(viewModelScope)
}