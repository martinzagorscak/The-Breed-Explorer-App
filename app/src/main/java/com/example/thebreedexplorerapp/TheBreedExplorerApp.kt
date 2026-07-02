package com.example.thebreedexplorerapp

import android.app.Application
import com.example.thebreedexplorerapp.data.di.dataModule
import com.example.thebreedexplorerapp.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TheBreedExplorerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize any application-level resources or configurations here
        startKoin {
            androidLogger()
            androidContext(this@TheBreedExplorerApp)
            modules(dataModule, domainModule)
        }
    }
}
