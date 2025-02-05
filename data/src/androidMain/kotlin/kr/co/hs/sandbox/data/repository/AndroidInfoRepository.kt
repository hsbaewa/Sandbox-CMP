package kr.co.hs.sandbox.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.co.hs.sandbox.data.Mapper.toDomain
import kr.co.hs.sandbox.data.datasource.AndroidInfoDataSource
import kr.co.hs.sandbox.data.datasource.PlatformInfoDataSource
import kr.co.hs.sandbox.domain.entity.PlatformInfoEntity
import kr.co.hs.sandbox.domain.repository.PlatformInfoRepository

internal class AndroidInfoRepository(
    private val dataSource: PlatformInfoDataSource = AndroidInfoDataSource()
) : PlatformInfoRepository {
    override fun flowOfPlatformInfo(): Flow<PlatformInfoEntity> = dataSource
        .flowOfPlatformData()
        .map { it.toDomain() }
}