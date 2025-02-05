package kr.co.hs.sandbox.data.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.co.hs.sandbox.data.dto.PlatformDataDTO
import platform.UIKit.UIDevice

internal class IOSInfoDataSource : PlatformInfoDataSource {
    override fun flowOfPlatformData(): Flow<PlatformDataDTO> = flow {
        val os = PlatformDataDTO.OS.IOS
        val version = UIDevice.currentDevice.systemVersion
        val dto = PlatformDataDTO(
            os = os,
            versionName = version
        )
        emit(dto)
    }
}