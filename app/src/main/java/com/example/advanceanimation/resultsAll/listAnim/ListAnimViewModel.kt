package com.example.advanceanimation.resultsAll.listAnim

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.advanceanimation.resultsAll.Rocket
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListAnimViewModel : ViewModel() {

    private val _rockets = MutableStateFlow<List<Rocket>>(emptyList())
    val rockets: StateFlow<List<Rocket>>
        get() = _rockets

    fun empty() {
        _rockets.update { emptyList() }
    }

    fun refreshListWithDelay(delay: Long) {
        viewModelScope.launch {
            delay(delay)
            _rockets.update {
                Rocket.ALL
            }
        }
    }


    // For reorder
    fun move(from: Int, to: Int) {
        _rockets.value.toMutableList().let { list ->
            val rocket = list.removeAt(from)
            list.add(to, rocket)
            _rockets.update {
                list
            }
        }
    }

    // For swipe to remove
    fun remove(at: Int) {
        _rockets.value.toMutableList().let { list ->
            list.removeAt(at)
            _rockets.update { list }
        }
    }

}