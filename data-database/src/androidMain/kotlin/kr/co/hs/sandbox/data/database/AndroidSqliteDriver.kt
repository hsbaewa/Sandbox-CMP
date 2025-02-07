package kr.co.hs.sandbox.data.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import kr.co.hs.sandbox.data.database.cache.Database
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class AndroidSqliteDriver : DatabaseDriverFactory, KoinComponent {

    private val context: Context = get()

    override fun createDriver(): SqlDriver =
        AndroidSqliteDriver(
            schema = Database.Schema,
            context = context,
            name = "board.db"
        )
}