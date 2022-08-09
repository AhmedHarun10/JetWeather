package com.ahmedh.jetweather.screens.Search

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ahmedh.jetweather.navigation.WeatherScreens
import com.ahmedh.jetweather.widgets.WeatherAppBar

@ExperimentalComposeUiApi
@Composable
fun SearchScreen(navController: NavController){
    
    Scaffold(
        topBar = {
            WeatherAppBar(
                navController = navController,
                icon = Icons.Default.ArrowBack,
                isMainScreen = false,
                title = "Search"
            ){
                navController.popBackStack()
            }
        }
    )
    {
       Surface() {
           Column(
               verticalArrangement = Arrangement.Center,
               horizontalAlignment = Alignment.CenterHorizontally
           ) {
               SearchBar(
                   modifier = Modifier.fillMaxWidth()
                       .padding(16.dp)
                       .align(Alignment.CenterHorizontally)
               ){  city ->
                navController.navigate(WeatherScreens.MainScreen.name+"/$city")
               }

           }
            
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch : (String) ->Unit = {}
){
    val searchQueryState = rememberSaveable{
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(searchQueryState.value){
        searchQueryState.value.trim().isNotEmpty()
    }
    Column(modifier = modifier) {
        CommonTextField(
            valueState = searchQueryState,
            placeHolder = "Enter city",
            onAction = KeyboardActions {
              if(!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            }
        )
    }
}

@Composable
fun CommonTextField(valueState: MutableState<String>,
                    placeHolder: String,
                    keyBoardType:KeyboardType = KeyboardType.Text,
                    imeAction: ImeAction = ImeAction.Done,
                    onAction: KeyboardActions = KeyboardActions.Default
) {

    OutlinedTextField(
        value = valueState.value,
        onValueChange ={valueState.value = it},
        label = { Text(text = placeHolder)},
        maxLines = 1,
        singleLine = true,
        keyboardOptions =
        KeyboardOptions(keyboardType = keyBoardType, imeAction = imeAction),
        keyboardActions =onAction,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Blue,
            cursorColor = Color.Black
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    )
}
