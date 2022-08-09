package com.ahmedh.jetweather.repository

import com.ahmedh.jetweather.data.WeatherDao
import com.ahmedh.jetweather.model.Favorite
import com.ahmedh.jetweather.model.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao)
{
    // Favorite repository
    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()

    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)

    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)

    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)

    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()

    suspend fun getFavoriteById(city:String) = weatherDao.getFavById(city)

    // Unit repository
    fun getUnits(): Flow<List<Unit>> = weatherDao.getUnits()

    suspend fun insertUnit(unit: Unit) = weatherDao.insertUnit(unit)

    suspend fun updateUnit(unit: Unit) = weatherDao.updateUnit(unit)

    suspend fun deleteAllUnits() = weatherDao.deleteAllUnits()

    suspend fun deleteUnit(unit: Unit)= weatherDao.deleteUnit(unit)

}