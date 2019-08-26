package com.example.bidivideoviewer

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.SystemClock

class App: Application() {
    companion object {
        private lateinit var instance: App
        fun getContext(): Context {return instance.applicationContext}

        lateinit var preferences: SharedPreferences

        val arbitraryCameraRequestCode = 200
    }

    override fun onCreate() {
        super.onCreate()
        preferences = this.getSharedPreferences(resources.getString(R.string.app_name), 0)
    }

    init {instance = this}
}
