package kr.co.hs.sandbox.data.preference.di

import kr.co.hs.sandbox.data.preference.getPreference
import kr.co.hs.sandbox.data.preference.repository.DefaultPreferenceRepository
import kr.co.hs.sandbox.domain.repository.PreferenceRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::getPreference)
    single<PreferenceRepository> { DefaultPreferenceRepository() }

    includes(kr.co.hs.sandbox.domain.di.usecaseModule)
}