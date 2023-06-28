package com.androiddevs.movieslistapp.model

data class Title(
    val query: String,
    val results: List<Result>,
    val types: List<String>
)