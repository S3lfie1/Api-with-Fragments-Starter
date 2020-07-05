package com.mazur.stationsdistance

import android.app.Application
import com.orhanobut.hawk.Hawk

class MainActivity : Application() {
    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
    }
}
