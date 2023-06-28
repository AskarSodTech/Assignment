package com.androiddevs.movieslistapp.model

data class Principal(
    val `as`: String,
    val billing: Int,
    val category: String,
    val characters: List<String>,
    val disambiguation: String,
    val endYear: Int,
    val episodeCount: Int,
    val id: String,
    val legacyNameText: String,
    val name: String,
    val roles: List<Role>,
    val startYear: Int
)