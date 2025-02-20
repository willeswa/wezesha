package com.wanjala.wezesha.data.models

import com.google.gson.annotations.SerializedName

data class ProductList(
    @SerializedName("products") val products: List<ProductItem>,
    @SerializedName("total") val total: Int
)
