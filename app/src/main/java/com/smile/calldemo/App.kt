package com.smile.calldemo

import android.app.Application
import com.smile.calldemo.common.mmkv.MMKVManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        MMKVManager.initialize(this)
    }
}