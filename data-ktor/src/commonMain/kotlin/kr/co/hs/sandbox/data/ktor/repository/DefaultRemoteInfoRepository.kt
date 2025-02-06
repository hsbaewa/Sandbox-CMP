package kr.co.hs.sandbox.data.ktor.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.co.hs.sandbox.data.ktor.Mapper.toDomain
import kr.co.hs.sandbox.data.ktor.datasource.DefaultExampleRemoteSource
import kr.co.hs.sandbox.data.ktor.datasource.ExampleRemoteSource
import kr.co.hs.sandbox.domain.entity.RemoteInfoEntity
import kr.co.hs.sandbox.domain.repository.RemoteInfoRepository

internal class DefaultRemoteInfoRepository(
    private val dataSource: ExampleRemoteSource = DefaultExampleRemoteSource()
) : RemoteInfoRepository {
    override fun flowOfRemoteInfo(): Flow<RemoteInfoEntity> = dataSource
        .flowOfExampleResponse()
        .map { it.toDomain() }
}