package dev.mslalith.interactive_math.navigator

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.mslalith.interactive_math.contents.fibonacci.Fibonacci
import dev.mslalith.interactive_math.contents.Home
import dev.mslalith.interactive_math.util.LogCompositions

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    LogCompositions()

    fun clearAndNavigate(screen: Screen) = navController.navigate(screen.route) {
        popUpTo(0)
    }

    Home(
        navigateTo = { clearAndNavigate(screen = it) }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Fibonacci.route
        ) {
            fibonacci()
            test()
        }
    }

}

private fun NavGraphBuilder.fibonacci() {
    composable(Screen.Fibonacci.route) {
        Fibonacci()
    }
}

private fun NavGraphBuilder.test() {
    composable(Screen.Test.route) {
        Text("Test")
    }
}
