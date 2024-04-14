package com.example.edistynyt_mobiiliohjelmointi

import android.content.Context
import androidx.room.Room

object DbProvider {
    private lateinit var db: AccountDatabase

    fun provide(context: Context) {
        db = Room.databaseBuilder(context, AccountDatabase::class.java, "account.db").build()
    }
}