package com.example.trungnguyen.hackathonproject.base

import android.app.Application

/**
 * Author : Trung Nguyen
 * Date : 11/25/2017
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        @get:Synchronized
        var instance: App? = null
            private set
    }
}
