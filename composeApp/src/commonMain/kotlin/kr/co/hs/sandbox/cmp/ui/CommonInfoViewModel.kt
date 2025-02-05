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
import kr.co.hs.sandbox.data.repository.DefaultCommonInfoRepository
import kr.co.hs.sandbox.domain.usecase.GetCommonInfoUseCase

class CommonInfoViewModel(
    getCommonInfoUseCase: GetCommonInfoUseCase = GetCommonInfoUseCase(repository = DefaultCommonInfoRepository())
) : ViewModel() {
    private val _text = MutableStateFlow<String?>(null)
    val text: StateFlow<String?> = _text.asStateFlow()

    init {
        getCommonInfoUseCase()
            .map {
                when (it) {
                    is NoErrorUseCase.Result.Exception -> throw it.t
                    is NoErrorUseCase.Result.Success -> it.data
                }
            }
            .flowOn(Dispatchers.IO)
            .onEach { _text.value = it.text }
            .launchIn(viewModelScope)
    }
}