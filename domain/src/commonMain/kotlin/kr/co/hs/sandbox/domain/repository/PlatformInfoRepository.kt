package kr.co.hs.sandbox.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.hs.sandbox.domain.entity.PlatformInfoEntity

interface PlatformInfoRepository {
    fun flowOfPlatformInfo(): Flow<PlatformInfoEntity>
}