package com.androiddevs.movieslistapp.di

import android.content.Context
import com.androiddevs.movieslistapp.modelview.MovieRepository
import com.androiddevs.movieslistapp.modelview.MovieViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class MovieModule(private val context: Context) {
    @Provides
    fun provideContext(): Context = context

    @Provides
    fun provideMovieRepository(context: Context): MovieRepository = MovieRepository(context)

    @Provides
    fun provideMovieViewModelFactory(repository: MovieRepository): MovieViewModelFactory =
        MovieViewModelFactory(repository)
}
