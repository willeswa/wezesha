package com.wanjala.wezesha.di

import android.content.Context
import com.wanjala.wezesha.data.local.ProductLocalDataSource
import com.wanjala.wezesha.data.local.db.AppDatabase
import com.wanjala.wezesha.data.local.db.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(productDao: ProductDao): ProductLocalDataSource {
        return ProductLocalDataSource(productDao)
    }

}