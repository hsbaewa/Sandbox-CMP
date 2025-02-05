package kr.co.hs.sandbox.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.hs.sandbox.domain.entity.CommonInfoEntity

interface CommonInfoRepository {
    fun flowOfCommonInfo(): Flow<CommonInfoEntity>
}