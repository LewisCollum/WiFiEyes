package com.example.bidivideoviewer

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.SystemClock

class App: Application() {
    companion object {
        lateinit var preferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        preferences = this.getSharedPreferences(resources.getString(R.string.app_name), 0)
    }
}
