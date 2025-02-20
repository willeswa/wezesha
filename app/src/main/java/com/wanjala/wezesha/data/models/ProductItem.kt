package com.wanjala.wezesha.data.models

import com.google.gson.annotations.SerializedName

data class ProductItem(
    @SerializedName("id") val id: Int,
    @SerializedName("price") val price: Float,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("title") val title: String,
)