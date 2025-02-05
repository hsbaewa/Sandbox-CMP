package kr.co.hs.sandbox.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.co.hs.sandbox.domain.entity.CommonInfoEntity
import kr.co.hs.sandbox.domain.entity.impl.DefaultCommonInfoEntity
import kr.co.hs.sandbox.domain.repository.CommonInfoRepository

class DefaultCommonInfoRepository : CommonInfoRepository {
    override fun flowOfCommonInfo(): Flow<CommonInfoEntity> = flow {
        emit(DefaultCommonInfoEntity("this is common entity text"))
    }
}