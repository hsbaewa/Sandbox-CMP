package kr.co.hs.sandbox.data.database.di

import kr.co.hs.sandbox.data.database.getDatabaseDriver
import kr.co.hs.sandbox.data.database.repository.DefaultBoardRepository
import kr.co.hs.sandbox.domain.repository.BoardRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::getDatabaseDriver)
    single<BoardRepository> { DefaultBoardRepository() }

    includes(kr.co.hs.sandbox.domain.di.usecaseModule)
}