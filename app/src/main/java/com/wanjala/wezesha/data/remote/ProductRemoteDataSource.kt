package com.wanjala.wezesha.data.remote

import com.wanjala.wezesha.data.models.ProductDetail
import com.wanjala.wezesha.data.models.ProductList
import com.wanjala.wezesha.data.remote.api.ApiService
import retrofit2.Response
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val apiService: ApiService
){
    suspend fun fetchProducts(): Response<ProductList> {
        return try {
            apiService.getProducts()
        } catch (e: Exception){
            throw e
        }
    }

    suspend fun fetchProductDetail(productId: Int): Response<ProductDetail> {
        return try {
             apiService.getProductById(productId)
        } catch (e: Exception){
            throw e
        }
    }

    suspend fun searchProducts(searchQuery: String): Response<ProductList> {
        return try {
             apiService.searchProducts(searchQuery)
        } catch (e: Exception){
            throw e
        }
    }
}