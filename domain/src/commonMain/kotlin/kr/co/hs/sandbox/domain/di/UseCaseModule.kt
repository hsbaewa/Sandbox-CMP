package kr.co.hs.sandbox.domain.di

import kr.co.hs.sandbox.domain.usecase.GetButtonClickCountUseCase
import kr.co.hs.sandbox.domain.usecase.GetCommonInfoUseCase
import kr.co.hs.sandbox.domain.usecase.GetPlatformInfoUseCase
import kr.co.hs.sandbox.domain.usecase.GetRemoteInfoUseCase
import kr.co.hs.sandbox.domain.usecase.UpCountButtonClickUseCase
import org.koin.dsl.module

val usecaseModule = module {
    single<GetCommonInfoUseCase> { GetCommonInfoUseCase() }
    single<GetPlatformInfoUseCase> { GetPlatformInfoUseCase() }
    single<GetRemoteInfoUseCase> { GetRemoteInfoUseCase() }

    single<GetButtonClickCountUseCase> { GetButtonClickCountUseCase() }
    single<UpCountButtonClickUseCase> { UpCountButtonClickUseCase() }
}