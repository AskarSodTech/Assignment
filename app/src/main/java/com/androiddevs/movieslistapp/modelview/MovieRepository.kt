package com.androiddevs.movieslistapp.modelview

import android.content.Context
import com.androiddevs.movieslistapp.model.Movie
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

class MovieRepository(private val context: Context) {


    fun getMoviesFromAssets(fileName: String): List<Movie> {
        val json = context.assets.open(fileName).bufferedReader().use { it.readText() }
        val pageData = Gson().fromJson(json, PageData::class.java)

        val contentItems = pageData.page.contentItems
        return contentItems?.content?.map { it.toMovie() } ?: emptyList()
    }

    data class PageData(
        val page: Page
    )

    data class Page(
        val title: String,
        @SerializedName("total-content-items") val totalContentItems: String?,
        @SerializedName("page-num") val pageNum: String?,
        @SerializedName("page-size")val pageSize: String?,
        @SerializedName("content-items") val contentItems: ContentItems?
    )

    data class ContentItems(
        val content: List<ContentItem>
    )

    data class ContentItem(
        val name: String,
        @SerializedName("poster-image") val posterImage: String
    )

    fun ContentItem.toMovie() = Movie(name, posterImage)
}
