package dev.mslalith.interactive_math.contents

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.mslalith.interactive_math.components.NavDrawerContent
import dev.mslalith.interactive_math.navigator.Screen
import dev.mslalith.interactive_math.util.LogCompositions
import dev.mslalith.interactive_math.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navigateTo: (Screen) -> Unit,
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val homeViewModel = hiltViewModel<HomeViewModel>()

    fun openDrawer() { coroutineScope.launch { drawerState.open() } }
    fun closeDrawer() { coroutineScope.launch { drawerState.close() } }

    BackHandler(enabled = drawerState.isOpen) { closeDrawer() }
    LogCompositions()

    val currentScreen by homeViewModel.currentScreen.collectAsState()

    NavigationDrawer(
        drawerState = drawerState,
        drawerShape = RoundedCornerShape(
            topEnd = 16.dp,
            bottomEnd = 16.dp
        ),
        drawerContent = {
            NavDrawerContent(
                currentScreen = currentScreen,
                onNavItemClick = { screen ->
                    homeViewModel.setScreen(screen)
                    navigateTo(screen)
                    closeDrawer()
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { currentScreen.name },
                    onMenuClick = ::openDrawer
                )
            },
        ) {
            content()
        }
    }
}

@Composable
private fun TopAppBar(
    title: () -> String,
    onMenuClick: () -> Unit
) {
    SmallTopAppBar(
        title = {
            Crossfade(targetState = title()) { text ->
                Text(text = text)
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onMenuClick,
                content = {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Home Icon"
                    )
                }
            )
        }
    )
}
