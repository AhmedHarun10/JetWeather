package com.ahmedh.jetweather.repository

import android.util.Log
import com.ahmedh.jetweather.data.DataOrException
import com.ahmedh.jetweather.model.Weather
import com.ahmedh.jetweather.network.WeatherApi
import java.lang.Exception
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    suspend fun getWeather(cityQuery: String, units: String)
    :DataOrException<Weather,Boolean,Exception>{
        val response = try {
            api.getWeather(query = cityQuery, units = units)
        }catch (e:Exception){
            Log.d("ERROR",e.toString())
            return DataOrException(e = e)
        }

        return DataOrException(data = response)
    }
}