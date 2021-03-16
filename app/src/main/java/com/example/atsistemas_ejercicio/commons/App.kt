package com.example.atsistemas_ejercicio.commons

import android.app.Application
import com.example.atsistemas_ejercicio.di.uiModule
import com.example.data.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App) // Context
            modules(listOf(dataModule, uiModule)) // Modules
        }
    }
}