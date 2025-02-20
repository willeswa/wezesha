package com.wanjala.wezesha.di

import com.wanjala.wezesha.data.local.ProductLocalDataSource
import com.wanjala.wezesha.data.remote.ProductRemoteDataSource
import com.wanjala.wezesha.data.repositories.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule  {

    @Provides
    @Singleton
    fun provideRepository(remoteDataSource: ProductRemoteDataSource,
                          localDataSource: ProductLocalDataSource
    ): ProductRepository {
        return ProductRepository(remoteDataSource, localDataSource)
    }

}