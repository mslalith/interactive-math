package dev.mslalith.interactive_math.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FibonacciViewModel @Inject constructor() : ViewModel() {

    private val fibonacciHashMap by lazy {
        LinkedHashMap<Int, Int>().apply {
            put(0, 0)
            put(1, 1)
        }
    }

    private val _rotationAngle = MutableStateFlow(value = 0f)
    val rotationAngle: StateFlow<Float>
        get() = _rotationAngle


    fun nextFibonacciState() {
        _rotationAngle.update { it - 90f }
    }

    fun sumOfFibonacciAt(index: Int): Int {
        fibonacciNumberAt(index)
        return fibonacciHashMap.values.sum()
    }

    fun fibonacciNumberAt(index: Int): Int {
        val fibNumber = fibonacciHashMap[index]
        if (fibNumber != null) return fibNumber

        return (fibonacciNumberAt(index - 1) + fibonacciNumberAt(index - 2))
            .also { fibonacciHashMap[index] = it }
    }
}