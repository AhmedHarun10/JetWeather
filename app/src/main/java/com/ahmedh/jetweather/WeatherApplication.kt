package com.ahmedh.jetweather

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// creates weather application class and makes it a hilt app
// allows app to use hilt di
@HiltAndroidApp
class WeatherApplication : Application() {

}