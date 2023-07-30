package com.androiddevs.movieslistapp

import android.app.Application
import com.androiddevs.movieslistapp.di.AppComponent
import com.androiddevs.movieslistapp.di.MovieModule

class   MyApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        /*appComponent = DaggerAppComponent.builder()
            .movieModule(MovieModule(applicationContext))
            .build()*/
    }
}
