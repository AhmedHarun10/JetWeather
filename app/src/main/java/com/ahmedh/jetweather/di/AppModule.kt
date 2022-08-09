package com.ahmedh.jetweather.di

import android.content.Context
import androidx.room.Room
import com.ahmedh.jetweather.data.WeatherDao
import com.ahmedh.jetweather.data.WeatherDatabase
import com.ahmedh.jetweather.network.WeatherApi
import com.ahmedh.jetweather.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase):WeatherDao
    = weatherDatabase.weatherDao()

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context):WeatherDatabase
    = Room.databaseBuilder(
        context,
        WeatherDatabase::class.java,
        "weather_database")
        .fallbackToDestructiveMigration()
        .build()



    @Provides
    @Singleton
    fun provideOpenWeatherApi():WeatherApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}
