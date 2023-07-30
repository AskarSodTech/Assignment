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

    fun parseJsonFile(context: Context, fileName: String): PageData? {
        try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            val jsonStr = String(buffer, Charsets.UTF_8)

            // Parse the JSON data using Gson
            val gson = Gson()
            val pageDataM = gson.fromJson(jsonStr, PageData::class.java)

            // Now you can access the data from the parsed object
            val title = pageDataM.page.title
            val totalContentItems = pageDataM.page.totalContentItems
            val pageNum = pageDataM.page.pageNum
            val pageSize = pageDataM.page.pageSize
            val contentItems = pageDataM.page.contentItems

            // Access individual content items if available
            contentItems?.content?.let { contentList ->
                for (content in contentList) {
                    val name = content.name
                    val posterImage = content.posterImage
                    // Process the data as needed
                }
            }
            return pageDataM
        } catch (e: Exception) {

            return null
            e.printStackTrace()
        }


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
