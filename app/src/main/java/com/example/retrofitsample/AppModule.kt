package com.example.retrofitsample

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
}