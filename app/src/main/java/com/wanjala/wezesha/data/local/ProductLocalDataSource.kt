package com.wanjala.wezesha.data.local

import com.wanjala.wezesha.data.local.db.ProductDao
import com.wanjala.wezesha.data.local.db.entities.ProductEntity
import com.wanjala.wezesha.data.models.ProductItem
import javax.inject.Inject

class ProductLocalDataSource @Inject constructor(private val productDao: ProductDao){
    suspend fun getProducts(): List<ProductItem>{
        return productDao.getProducts().map {
            ProductItem(
                id = it.id,
                title = it.title,
                price = it.price,
                thumbnail = it.thumbnail
            )
        }
    }
    suspend fun saveProducts(products: List<ProductItem>) {
        productDao.clearProducts()
        val productEntities = products.map {
            ProductEntity(
                id = it.id,
                title = it.title,
                price = it.price,
                thumbnail = it.thumbnail)
        }
        productDao.saveProducts(productEntities)
    }
}
