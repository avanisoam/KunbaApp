package com.example.kunbaapp

import android.app.Application
import com.example.kunbaapp.di.dataSourceModule
import com.example.kunbaapp.di.networkModule
import com.example.kunbaapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KunbaAppApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@KunbaAppApplication)

            modules(
                networkModule,
                dataSourceModule,
                viewModelModule
            )
        }
    }
}