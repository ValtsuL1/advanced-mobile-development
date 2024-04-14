package com.example.edistynyt_mobiiliohjelmointi

import android.app.Application

class CustomApp: Application() {
    override fun onCreate() {
        super.onCreate()
        DbProvider.provide(this)
    }
}