package com.ahmedh.jetweather.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.ahmedh.jetweather.R
import com.ahmedh.jetweather.model.Weather
import com.ahmedh.jetweather.model.WeatherItem
import com.ahmedh.jetweather.util.formatDate
import com.ahmedh.jetweather.util.formatDateTime
import com.ahmedh.jetweather.util.formatDecimals


@Composable
fun FiveDayForcast(data: Weather) {
    val size = data.list.size
    var i = 0
    var days = mutableListOf<String>()
    var forcastList = data.list.toMutableList()
    val temp = mutableListOf<WeatherItem>()
    var useFirst  = remember{
        mutableStateOf(true)
    }
    Surface(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        color = Color(0xFFEEF1EF),
        shape = RoundedCornerShape(size = 14.dp)
    ) {
        LazyColumn(
            modifier = Modifier.padding(2.dp),
            contentPadding = PaddingValues(all = 1.dp)
        ){
                itemsIndexed(data.list) { index ,weather ->
                   if(index % 8 == 0){
                       WeatherDetailRow(weather = weather)
                   }
                }

        }
    }

}


@Composable
fun WeatherDetailRow(weather: WeatherItem) {
    val imageUrl ="https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(
            topEnd = CornerSize(6.dp)
        ),
        color = Color.White
    )
    {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween)
        {
            Text(text = formatDate(weather.dt).split(",")[0],
                modifier = Modifier.padding(start = 5.dp)
            )
            WeatherStateImage(imageUrl = imageUrl)
            Surface(
                modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400)
            ) {
                Text(text = weather.weather[0].description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption
                )
            }
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = Color.Blue.copy(alpha = .7f),
                    fontWeight = FontWeight.SemiBold
                )
                ){
                    append(formatDecimals(weather.main.temp) +"º")
                }
            }, modifier = Modifier.padding(end = 15.dp))

        }
    }

}


@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(painter = rememberImagePainter(imageUrl),
        contentDescription ="icon image",
        modifier = Modifier.size(80.dp)
    )
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem, isImperial: Boolean){
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Row(
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.humidity),
                contentDescription ="humidity icon", modifier = Modifier.size(20.dp) )
            Text(text = "${weather.main.humidity}%",
                style = MaterialTheme.typography.caption
            )
        }
        Row(
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.pressure),
                contentDescription ="Pressure icon", modifier = Modifier.size(20.dp) )
            Text(text = "${weather.main.pressure} psi",
                style = MaterialTheme.typography.caption
            )
        }
        Row(
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.wind),
                contentDescription ="Wind icon", modifier = Modifier.size(20.dp) )
            Text(text = formatDecimals(weather.wind.speed)+ if(isImperial) "mph" else "m/s",
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun SunSetSunRiseRow(data: Weather) {
    Row(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "Sunrise icon", modifier = Modifier.size(30.dp)
            )
            Text(
                text = formatDateTime(timestamp = data.city.sunrise),
                style = MaterialTheme.typography.caption
            )
        }
        Row(
            modifier = Modifier.padding(4.dp)
        ) {

            Text(
                text = formatDateTime(timestamp = data.city.sunset),
                style = MaterialTheme.typography.caption
            )
            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "Sunset icon", modifier = Modifier.size(30.dp)
            )
        }

    }
}