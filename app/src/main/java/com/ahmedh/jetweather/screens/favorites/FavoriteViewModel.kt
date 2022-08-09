package com.ahmedh.jetweather.screens.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedh.jetweather.model.Favorite
import com.ahmedh.jetweather.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherDbRepository) :ViewModel()
{
    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.getFavorites().distinctUntilChanged()
                .collect { favList ->
                    if(!favList.isNullOrEmpty()){
                        _favList.value = favList
                        Log.d("TAG",favList.toString())
                    }
                }
        }
    }

    fun insertFavorite(favorite: Favorite) = viewModelScope.launch {
        repository.insertFavorite(favorite)
    }
    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch {
        repository.deleteFavorite(favorite)
    }
    fun deleteAllFavorites() = viewModelScope.launch {
        repository.deleteAllFavorites()
    }
    fun updateFavorite(favorite: Favorite) = viewModelScope.launch {
        repository.updateFavorite(favorite)
    }
    fun getFavoriteById(city:String) = viewModelScope.launch {
        repository.getFavoriteById(city)
    }

}