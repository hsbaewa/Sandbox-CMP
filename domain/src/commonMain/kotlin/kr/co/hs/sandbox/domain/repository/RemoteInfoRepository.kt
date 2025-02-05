package kr.co.hs.sandbox.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.hs.sandbox.domain.entity.RemoteInfoEntity

interface RemoteInfoRepository {
    fun flowOfRemoteInfo(): Flow<RemoteInfoEntity>
}