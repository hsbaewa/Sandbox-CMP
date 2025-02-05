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
import kr.co.hs.sandbox.data.getPlatformInfoRepository
import kr.co.hs.sandbox.domain.usecase.GetPlatformInfoUseCase

class PlatformInfoViewModel(
    getPlatformInfoUseCase: GetPlatformInfoUseCase = GetPlatformInfoUseCase(repository = getPlatformInfoRepository())
) : ViewModel() {
    private val _os = MutableStateFlow<String?>(null)
    val os: StateFlow<String?> = _os.asStateFlow()

    init {
        getPlatformInfoUseCase()
            .map {
                when (it) {
                    is NoErrorUseCase.Result.Exception -> throw it.t
                    is NoErrorUseCase.Result.Success -> it.data
                }
            }
            .flowOn(Dispatchers.IO)
            .onEach { _os.value = it.os }
            .launchIn(viewModelScope)
    }
}