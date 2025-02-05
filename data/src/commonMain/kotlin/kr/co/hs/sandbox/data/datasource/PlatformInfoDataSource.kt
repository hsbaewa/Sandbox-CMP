package kr.co.hs.sandbox.data.datasource

import kotlinx.coroutines.flow.Flow
import kr.co.hs.sandbox.data.dto.PlatformDataDTO

internal interface PlatformInfoDataSource {
    fun flowOfPlatformData(): Flow<PlatformDataDTO>
}