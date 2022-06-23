package com.example.advanceanimation.resultsAll.fabTransform

import androidx.lifecycle.ViewModel
import com.example.advanceanimation.resultsAll.Rocket
import kotlinx.coroutines.flow.MutableStateFlow


class FabTransformViewModel : ViewModel() {
    val rockets: MutableStateFlow<List<Rocket>> = MutableStateFlow(Rocket.ALL.shuffled().take(3))
}