package kr.co.hs.sandbox.data.ktorfit.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.co.hs.sandbox.data.ktorfit.Mapper.toDomain
import kr.co.hs.sandbox.data.ktorfit.datasource.DefaultExampleRemoteSource
import kr.co.hs.sandbox.data.ktorfit.datasource.ExampleRemoteSource
import kr.co.hs.sandbox.domain.entity.RemoteInfoEntity
import kr.co.hs.sandbox.domain.repository.RemoteInfoRepository

internal class DefaultRemoteInfoRepository(
    private val dataSource: ExampleRemoteSource = DefaultExampleRemoteSource()
) : RemoteInfoRepository {
    override fun flowOfRemoteInfo(): Flow<RemoteInfoEntity> = dataSource
        .flowOfExampleResponse()
        .map { it.toDomain() }
}