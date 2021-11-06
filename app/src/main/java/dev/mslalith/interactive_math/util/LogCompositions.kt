package dev.mslalith.interactive_math.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import dev.mslalith.interactive_math.BuildConfig
import timber.log.Timber

class Ref(var value: Int)

@Composable
inline fun LogCompositions(message: String = "") {
    if (BuildConfig.DEBUG) {
        val ref = remember { Ref(0) }
        SideEffect { ref.value++ }
        val msg = if (message.isNotEmpty()) "=== $message" else message
        Timber.d("recomposition count: ${ref.value} $msg")
    }
}