package com.ahmedh.jetweather.screens.main

import androidx.lifecycle.ViewModel
import com.ahmedh.jetweather.data.DataOrException
import com.ahmedh.jetweather.model.Weather
import com.ahmedh.jetweather.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor
    (private val repository: WeatherRepository) :ViewModel(){
        suspend fun getWeatherData(city: String, units: String):DataOrException<Weather,Boolean,Exception>{
            return repository.getWeather(cityQuery = city,units = units)
        }


}