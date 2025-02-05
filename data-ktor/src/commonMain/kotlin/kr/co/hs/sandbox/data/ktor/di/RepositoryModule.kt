package kr.co.hs.sandbox.data.ktor.di

import kr.co.hs.sandbox.data.ktor.repository.DefaultRemoteInfoRepository
import kr.co.hs.sandbox.domain.repository.RemoteInfoRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<RemoteInfoRepository> { DefaultRemoteInfoRepository() }

    includes(kr.co.hs.sandbox.domain.di.usecaseModule)
}