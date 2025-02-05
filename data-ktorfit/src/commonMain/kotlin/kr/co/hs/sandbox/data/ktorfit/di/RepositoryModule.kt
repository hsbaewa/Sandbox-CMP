package kr.co.hs.sandbox.data.ktorfit.di

import kr.co.hs.sandbox.data.ktorfit.repository.DefaultRemoteInfoRepository
import kr.co.hs.sandbox.domain.repository.RemoteInfoRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<RemoteInfoRepository> { DefaultRemoteInfoRepository() }

    includes(kr.co.hs.sandbox.domain.di.usecaseModule)
}