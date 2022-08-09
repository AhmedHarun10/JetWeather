package com.ahmedh.jetweather.widgets

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahmedh.jetweather.model.Favorite
import com.ahmedh.jetweather.navigation.WeatherScreens
import com.ahmedh.jetweather.screens.favorites.FavoriteViewModel


@Composable
fun WeatherAppBar(
    title:String = "Title",
    icon:ImageVector? = null,
    isMainScreen:Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked : () -> Unit ={}
)
{
    val showDialog = remember{
        mutableStateOf(false)
    }
    if(showDialog.value){
        ShowSettingDropDownMenu(showDialog = showDialog,navController = navController)
    }
    val showIt = remember{
        mutableStateOf(false)
    }
    val context = LocalContext.current
    TopAppBar (title = {
        Text(
            text = title,
            color = MaterialTheme.colors.onSecondary,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        )

    },  actions = {
                  if(isMainScreen){
                      IconButton(onClick = { onAddActionClicked.invoke() }) {
                          Icon(imageVector = Icons.Default.Search,
                              contentDescription ="Search Icon" )
                      }
                      IconButton(onClick = {
                          showDialog.value = true
                      }) {
                          Icon(imageVector = Icons.Default.MoreVert,
                              contentDescription ="More vert" )
                      }

                  }else{
                      Box {

                      }

                  }
    },
        navigationIcon = {
                         if (icon != null){
                             Icon(imageVector = icon,
                                 contentDescription = "an icon",
                             tint = MaterialTheme.colors.onSecondary,
                             modifier = Modifier.clickable {
                                 onButtonClicked.invoke()
                             })
                         }
            if(isMainScreen){
                val location = title.split(",")
                val isFavorite: Favorite? = favoriteViewModel.favList
                    .collectAsState().value.find {item ->   item.city == location[0]}
                Icon(imageVector = Icons.Default.Favorite
                    , contentDescription = "favorite_icon",
                modifier = Modifier
                    .scale(0.9f)
                    .padding(start = 5.dp)
                    .clickable {
                               if(isFavorite==null){
                                   val newFavorite = Favorite(
                                       city = location[0],
                                       country = location[1],
                                       isFavorite = true
                                   )
                                   favoriteViewModel.insertFavorite(newFavorite).run {
                                       showIt.value = true
                                   }
                               }
                    },
                tint = if(isFavorite != null){Color.Red}else{Color.Gray}
                )
                ShowToast(context = context, message = "City added to favorites", showIt = showIt)
                showIt.value = false
            }
        },
        backgroundColor = Color.Transparent,
        elevation =elevation )
}

@Composable
fun ShowToast(context: Context,message:String,showIt:MutableState<Boolean>) {
    if(showIt.value){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }
}

fun checkColor(favorite: List<Favorite>): Boolean {
    if(favorite.isNotEmpty()){
        Log.d("TAG",favorite!!.get(0).city)
        return true
    }
    return false
}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>,
                            navController: NavController) {
    var expanded by remember{
        mutableStateOf(true)
    }
    val items = listOf("About","Favorites","Settings")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
        )
    {
        DropdownMenu(expanded = expanded
            , onDismissRequest = {expanded = false},
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        )
        {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(onClick = {
                    expanded = false
                   showDialog.value = false
                })
                {
                     Icon(imageVector = when(text){
                         "About" -> Icons.Default.Info
                         "Favorites"->Icons.Default.Favorite
                         else -> Icons.Default.Settings },
                         contentDescription = null,
                         tint = Color.LightGray
                     )
                     Text(
                         text = text,
                         modifier = Modifier.clickable {
                            navController.navigate(route =
                                when(text){
                                    "About" -> WeatherScreens.AboutScreen.name
                                    "Favorites" -> WeatherScreens.FavoriteScreen.name
                                     else -> WeatherScreens.SettingsScreen.name
                                }
                            )
                         },
                         fontWeight = FontWeight.W300
                     )
                }
            }
        }
    }
}
