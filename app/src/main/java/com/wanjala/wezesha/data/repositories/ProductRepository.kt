package com.wanjala.wezesha.data.repositories

import com.wanjala.wezesha.data.local.ProductLocalDataSource
import com.wanjala.wezesha.data.models.ProductDetail
import com.wanjala.wezesha.data.models.ProductItem
import com.wanjala.wezesha.data.remote.ProductRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

open class ProductRepository @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource,
    private val localDataSource: ProductLocalDataSource) {

    open fun getProducts(): Flow<Result<List<ProductItem>>> = flow {
        emit(Result.Loading)



        try {
            val remoteResponse = remoteDataSource.fetchProducts()
            if(remoteResponse.isSuccessful){
                val productListResponse = remoteResponse.body()
                productListResponse?.products?.let { remoteProducts ->
                    localDataSource.saveProducts(remoteProducts)
                    emit(Result.Success(remoteProducts))
                } ?: run {
                    emit(Result.Error(Exception("No products found")))
                }
            } else {
                val cachedProducts = localDataSource.getProducts()
                if(cachedProducts.isNotEmpty()){
                    emit(Result.Success(cachedProducts))
                }  else {
                    emit(Result.Error(Exception("Failed to fetch products")))
                }         }
        } catch (e: Exception){
            val cachedProducts = localDataSource.getProducts()
            if(cachedProducts.isNotEmpty()){
                emit(Result.Success(cachedProducts))
            } else {
                emit(Result.Error(Exception(e.message)))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun getProductDetail(productId: Int): Flow<Result<ProductDetail>> = flow {
        emit(Result.Loading)

      try {
          val remoteResponse = remoteDataSource.fetchProductDetail(productId)

          if(remoteResponse.isSuccessful) {
              val productDetailResponse = remoteResponse.body()
              productDetailResponse?.let {
                  emit(Result.Success(it))
              } ?: run {
                  emit(Result.Error(Exception("No product detail found")))
              }
          } else {
              emit(Result.Error(Exception("Failed to fetch product detail")))
          }
      } catch (e: Exception){
          emit(Result.Error(Exception(e)))
      }
    }.flowOn(Dispatchers.IO)

    fun searchProducts(query: String): Flow<Result<List<ProductItem>>> = flow {
        emit(Result.Loading)

        try {
            val remoteResponse = remoteDataSource.searchProducts(query)
            if (remoteResponse.isSuccessful) {
                val searchResponse = remoteResponse.body()
                searchResponse?.products?.let {
                    emit(Result.Success(it))
                } ?: run {
                    emit(Result.Error(Exception("No products found")))
                }
            } else {
                emit(Result.Error(Exception("Failed to search products")))
            }
        } catch (e: Exception) {
            emit(Result.Error(Exception(e.message)))
                }
    }

    sealed class Result<out T> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val exception: Exception) : Result<Nothing>()
        data object Loading: Result<Nothing>()
    }
}