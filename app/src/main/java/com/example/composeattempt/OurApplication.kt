package com.example.composeattempt

import android.app.Application
import com.example.composeattempt.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class OurApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@OurApplication)

            val modules = listOf(
                appModule
            )

            modules(modules)
        }
    }
}