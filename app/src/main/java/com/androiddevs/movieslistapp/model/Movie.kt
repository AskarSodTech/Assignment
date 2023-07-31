package com.androiddevs.movieslistapp.model

data class Movie(
    val name: String,
    val posterImage: String
){
    fun getTruncatedName(maxLength: Int): String {
        return if (name.length > maxLength) {
            name.substring(0, maxLength - 3) + "..."
        } else {
            name
        }
    }
}