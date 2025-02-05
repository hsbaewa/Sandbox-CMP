package kr.co.hs.sandbox.domain.di

import kr.co.hs.sandbox.domain.usecase.GetCommonInfoUseCase
import kr.co.hs.sandbox.domain.usecase.GetPlatformInfoUseCase
import kr.co.hs.sandbox.domain.usecase.GetRemoteInfoUseCase
import org.koin.dsl.module

val usecaseModule = module {
    single<GetCommonInfoUseCase> { GetCommonInfoUseCase() }
    single<GetPlatformInfoUseCase> { GetPlatformInfoUseCase() }
    single<GetRemoteInfoUseCase> { GetRemoteInfoUseCase() }
}