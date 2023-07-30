package com.androiddevs.movieslistapp.model


data class MovieList(val page: Page)
data class Page(
    val title: String,
    val totalContentItems: String,
    val pageNum: String,
    val pageSize: String,
    val contentItems: ContentItems
)

data class ContentItems(val content: List<Movie>)
