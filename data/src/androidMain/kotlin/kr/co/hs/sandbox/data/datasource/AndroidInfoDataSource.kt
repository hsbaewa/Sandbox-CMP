package kr.co.hs.sandbox.data.datasource

import android.os.Build
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.co.hs.sandbox.data.dto.PlatformDataDTO

internal class AndroidInfoDataSource : PlatformInfoDataSource {
    override fun flowOfPlatformData(): Flow<PlatformDataDTO> = flow {
        val os = PlatformDataDTO.OS.Android
        val version = Build.VERSION.RELEASE
        val dto = PlatformDataDTO(
            os = os,
            versionName = version
        )
        emit(dto)
    }
}