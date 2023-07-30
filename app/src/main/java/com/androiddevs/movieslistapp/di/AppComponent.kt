package com.androiddevs.movieslistapp.di

import com.androiddevs.movieslistapp.view.MainActivity
import dagger.Component

@Component(modules = [MovieModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}
