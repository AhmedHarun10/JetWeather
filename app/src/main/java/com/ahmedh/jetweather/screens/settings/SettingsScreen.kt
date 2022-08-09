package com.ahmedh.jetweather.screens.settings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahmedh.jetweather.model.Unit
import com.ahmedh.jetweather.widgets.WeatherAppBar

@Composable
fun SettingsScreen(navController: NavController,settingsViewModel: SettingsViewModel = hiltViewModel()) {
    val unitToggleState = remember{
        mutableStateOf(false)
    }
    val measurementUnits = listOf("Imperial (F)","Metric (C)")
    val choiceFromDb = settingsViewModel.unitList.collectAsState().value
    val defaultChoice = if(choiceFromDb.isNullOrEmpty()) measurementUnits[1] else choiceFromDb[0].unit
    val choiceState = remember{
        mutableStateOf(defaultChoice)
    }
    var isToggled = false
    Scaffold(
        topBar = {
            WeatherAppBar(
                navController = navController,
                title = "Settings",
                icon = Icons.Default.ArrowBack,
                isMainScreen =  false
            ){
                navController.popBackStack()
            }
        }
    )
    {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = "Change Units of Measurement",
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                IconToggleButton(
                    checked = !unitToggleState.value, onCheckedChange = {
                        unitToggleState.value = !it
                        if (unitToggleState.value) {
                            choiceState.value = "Imperial (F)"
                        } else {
                            choiceState.value = "Metric (C)"
                        }
                        isToggled = true
                    }, modifier = Modifier
                        .fillMaxWidth(.5f)
                        .clip(shape = RectangleShape)
                        .padding(5.dp)
                        .background(Color.Magenta.copy(0.4f))
                )
                {
                    val text = if (unitToggleState.value) "Fahrenheit ºF" else "Celsius ºC"
                    Text(text = text)
                }

                Button(
                    onClick =
                    {
                        settingsViewModel.deleteAllUnits()

                        settingsViewModel.insertUnit(Unit(unit = choiceState.value))
                    },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFEFBE42)
                    )
                )
                {
                    Text(
                        text = "Save",
                        modifier = Modifier.padding(4.dp),
                        color = Color.White,
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}