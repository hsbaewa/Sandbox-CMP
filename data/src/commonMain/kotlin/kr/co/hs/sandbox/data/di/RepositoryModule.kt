package kr.co.hs.sandbox.data.di

import kr.co.hs.sandbox.data.getPlatformInfoRepository
import kr.co.hs.sandbox.data.repository.DefaultCommonInfoRepository
import kr.co.hs.sandbox.domain.repository.CommonInfoRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::getPlatformInfoRepository)
    single<CommonInfoRepository> { DefaultCommonInfoRepository() }

    includes(kr.co.hs.sandbox.domain.di.usecaseModule)
}