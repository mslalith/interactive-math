package dev.mslalith.interactive_math.extensions

import androidx.compose.ui.geometry.Size

fun Int.toSize() = this.toFloat().toSize()
fun Float.toSize() = Size(width = this, height = this)

fun Size.plus(operand: Size) = Size(width + operand.width, height + operand.height)
