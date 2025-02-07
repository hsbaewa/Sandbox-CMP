package kr.co.hs.sandbox.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import kr.co.hs.sandbox.data.database.cache.Database

class IOSDatabaseDriver : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver =
        NativeSqliteDriver(
            schema = Database.Schema,
            name = "board.db"
        )
}