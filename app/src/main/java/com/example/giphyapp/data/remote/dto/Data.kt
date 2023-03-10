package com.example.giphyapp.data.remote.dto

import com.example.giphyapp.data.local.GiphyEntity

data class Data(
    val analystic_response_payload: String,
    val bitly_gif_url: String,
    val bitly_url: String,
    val content_url: String,
    val embed_url: String,
    val id: String,
    val images: Images,
    val import_datetime: String,
    val is_sticker: Int,
    val rating: String,
    val source: String,
    val source_post_url: String,
    val source_tld: String,
    val title: String,
    val trending_datetime:String,
    val type: String,
    val url: String,
    val user: User,
    val username: String
) {
    fun toGiphyEntity(searchQuery: String):GiphyEntity {
        return GiphyEntity(
            id = id,
            title = title,
            url = images.original.url,
            searchQuery = searchQuery,
            blocked = false
        )
    }
}
