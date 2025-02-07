package kr.co.hs.sandbox.data.database

actual fun getDatabaseDriver(): DatabaseDriverFactory = AndroidSqliteDriver()