package com.example.retrofitsample.di

import com.example.retrofitsample.Constants
import com.example.retrofitsample.data.WeatherApi
import com.example.retrofitsample.data.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun wordApiRepository(api: WeatherApi) =
        WeatherRepository(api)

    @Singleton
    @Provides
    fun injectBackendRetrofitApi(): WeatherApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASEURL)
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    internal fun getDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}