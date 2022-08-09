package com.ahmedh.jetweather.screens.main


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahmedh.jetweather.data.DataOrException
import com.ahmedh.jetweather.model.Weather
import com.ahmedh.jetweather.navigation.WeatherScreens
import com.ahmedh.jetweather.screens.settings.SettingsViewModel
import com.ahmedh.jetweather.util.formatDate
import com.ahmedh.jetweather.util.formatDecimals
import com.ahmedh.jetweather.widgets.*

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?
) {
//
//    val curCity :String = if(city!!.isBlank()) "hargeisa" else city
//
//    val unitFromDb = settingsViewModel.unitList.collectAsState().value
//
//    val isEmpty = remember{
//        mutableStateOf(false)
//    }
//
//    var unit by remember{
//        mutableStateOf("metric")
//    }
//
//    var isImperial by remember{
//        mutableStateOf(false)
//    }
//
//    unit =  if(unitFromDb.isNullOrEmpty()) unit else unitFromDb[0].unit
//    isImperial = unit == "imperial"
//    val weatherData =
//        produceState<DataOrException<Weather,Boolean,Exception>>(
//            initialValue =DataOrException(loading = true)){
//            value = mainViewModel.getWeatherData(city = curCity,units = unit)
//        }.value
//    if(weatherData.loading == true){
//        CircularProgressIndicator()
//    }else if(weatherData.data != null){
//        MainScaffold(weatherData = weatherData.data!!,navController= navController,isImperial= isImperial)
//    }

    val curCity: String = if (city!!.isBlank()) "Seattle" else city
    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    var unit by remember {
        mutableStateOf("imperial")
    }
    var isImperial by remember {
        mutableStateOf(false)
    }

    if (!unitFromDb.isNullOrEmpty()) {
        unit = unitFromDb[0].unit.split(" ")[0].lowercase()
        isImperial = unit == "imperial"

        val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = mainViewModel.getWeatherData(
                city = curCity,
                units = unit
            )
        }.value

        if (weatherData.loading == true) {
            CircularProgressIndicator()
        } else if (weatherData.data != null) {
            MainScaffold(
                weatherData = weatherData.data!!,
                navController = navController,
                isImperial = isImperial
            )

        }
    }

}


@Composable
fun MainScaffold(weatherData: Weather, navController: NavController, isImperial: Boolean) {
    Scaffold(
        topBar = {
            WeatherAppBar(
                title = weatherData.city.name + ", " + weatherData.city.country,
                navController = navController,
                elevation = 5.dp,
                onAddActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                }
            )
        }
    ) {
        MainContent(data = weatherData, isImperial = isImperial)

    }
}


@Composable
fun MainContent(data: Weather, isImperial: Boolean) {

    val weatherItem = data.list[0]
    val imageUrl ="https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = formatDate(weatherItem.dt),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )

        Surface (
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400))
        {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Image
                WeatherStateImage(imageUrl = imageUrl)
                Text(text = formatDecimals(weatherItem.main.temp)+"º",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(text = weatherItem.weather[0].main,
                    fontStyle = FontStyle.Italic
                )
            }
        }
        HumidityWindPressureRow(weather = weatherItem,isImperial = isImperial)
        Divider()
        SunSetSunRiseRow(data = data)
        Text(text = "This Week", style = MaterialTheme.typography.subtitle1 ,fontWeight = FontWeight.Bold)
        FiveDayForcast(data = data)
    }


}