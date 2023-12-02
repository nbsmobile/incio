package com.nbs.kmm.shared.cache

import android.content.Context
import android.util.Log
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.nbs.kmm.sample.cache.AppDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

class AndroidDatabaseDriverFactory(
    private val context: Context,
    private val dbName: String,
    private val passphrase: String
) : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        val passPhrase = SQLiteDatabase.getBytes(passphrase.toCharArray())
        val factory = SupportFactory(passPhrase)

        return AndroidSqliteDriver(
            AppDatabase.Schema,
            context,
            dbName,
            factory = factory
        )
    }
}