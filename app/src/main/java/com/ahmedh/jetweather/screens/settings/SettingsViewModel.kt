package com.ahmedh.jetweather.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmedh.jetweather.model.Favorite
import com.ahmedh.jetweather.model.Unit
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
class SettingsViewModel @Inject constructor(private val repository: WeatherDbRepository) :
    ViewModel()
{
    private val _unitList = MutableStateFlow<List<Unit>>(emptyList())
    val unitList = _unitList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.getUnits().distinctUntilChanged()
                .collect { unitsList ->
                    if(!unitsList.isNullOrEmpty()){
                        _unitList.value = unitsList
                        Log.d("TAG",unitsList.toString())
                    }
                }
        }
    }

    fun insertUnit(unit: Unit) = viewModelScope.launch {
        repository.insertUnit(unit)
    }
    fun deleteUnit(unit: Unit) = viewModelScope.launch {
        repository.deleteUnit(unit)
    }
    fun deleteAllUnits() = viewModelScope.launch {
        repository.deleteAllUnits()
    }
    fun updateUnit(unit: Unit) = viewModelScope.launch {
        repository.updateUnit(unit)
    }


}