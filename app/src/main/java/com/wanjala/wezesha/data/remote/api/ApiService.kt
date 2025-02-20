package com.wanjala.wezesha.data.remote.api

import com.wanjala.wezesha.data.models.ProductDetail
import com.wanjala.wezesha.data.models.ProductList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    suspend fun getProducts(): Response<ProductList>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Response<ProductDetail>

    @GET("products/search")
    suspend fun searchProducts(@Query("q") query: String): Response<ProductList>

}