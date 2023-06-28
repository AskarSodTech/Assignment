package com.androiddevs.movieslistapp.model

data class KnownFor(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: String,
    val summary: Summary,
    val title: String,
    val titleType: String,
    val year: Int
)