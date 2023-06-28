package com.androiddevs.movieslistapp.model

data class Result(
    val akas: List<String>,
    val disambiguation: String,
    val id: String,
    val image: Image,
    val knownFor: List<KnownFor>,
    val legacyNameText: String,
    val name: String,
    val nextEpisode: String,
    val numberOfEpisodes: Int,
    val principals: List<Principal>,
    val runningTimeInMinutes: Int,
    val seriesEndYear: Int,
    val seriesStartYear: Int,
    val title: String,
    val titleType: String,
    val year: Int
)