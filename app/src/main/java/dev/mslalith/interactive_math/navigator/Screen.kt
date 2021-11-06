package dev.mslalith.interactive_math.navigator

sealed class Screen(val name: String, val route: String) {
    object Fibonacci : Screen("Fibonacci", "fibonacci")
    object Test : Screen("Test", "test")
}

val AllNavItems: List<Screen>
    get() = listOf(
        Screen.Fibonacci,
        Screen.Test
    )
