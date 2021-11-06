package dev.mslalith.interactive_math.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mslalith.interactive_math.navigator.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Fibonacci)
    val currentScreen: StateFlow<Screen>
        get() = _currentScreen

    fun setScreen(screen: Screen) {
        _currentScreen.value = screen
    }
}