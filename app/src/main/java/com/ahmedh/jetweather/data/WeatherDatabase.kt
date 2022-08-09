package com.ahmedh.jetweather.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahmedh.jetweather.model.Favorite
import com.ahmedh.jetweather.model.Unit

@Database(entities = [Favorite::class, Unit::class], version = 3, exportSchema = false)

abstract class WeatherDatabase :RoomDatabase() {
    abstract fun weatherDao() : WeatherDao

}