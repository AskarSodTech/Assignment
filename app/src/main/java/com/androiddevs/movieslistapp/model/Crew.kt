package com.androiddevs.movieslistapp.model

data class Crew(
    val `as`: String,
    val category: String,
    val endYear: Int,
    val episodeCount: Int,
    val freeTextAttributes: List<String>,
    val job: String,
    val startYear: Int
)