package com.wanjala.wezesha.data.models

import com.google.gson.annotations.SerializedName

data class ProductDetail(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Float,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("reviews") val reviews: List<Review>,
    @SerializedName("brand") val brand: String,
    @SerializedName("category") val category: String,
    @SerializedName("description") val description: String,
    @SerializedName("images") val images: List<String>
)

data class Review(
    @SerializedName("rating") val rating: Float,
    @SerializedName("comment") val text: String,
    @SerializedName("date") val date: String,
    @SerializedName("reviewerName") val reviewerName: String,
)