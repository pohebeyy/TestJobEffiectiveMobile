package com.example.testtaskandroid

import android.app.Application
import com.example.testtaskandroid.Dagger.AppComponent
import com.example.testtaskandroid.Dagger.AppModule
import com.example.testtaskandroid.Dagger.DaggerAppComponent

class MyApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}