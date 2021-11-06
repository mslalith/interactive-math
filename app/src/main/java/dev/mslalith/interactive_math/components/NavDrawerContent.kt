package dev.mslalith.interactive_math.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.mslalith.interactive_math.extensions.VerticalSpace
import dev.mslalith.interactive_math.navigator.AllNavItems
import dev.mslalith.interactive_math.navigator.Screen
import dev.mslalith.interactive_math.util.LogCompositions

@Composable
fun NavDrawerContent(
    currentScreen: Screen,
    onNavItemClick: (Screen) -> Unit
) {
    LogCompositions(message = currentScreen.name)

    Column {
        VerticalSpace(height = 12.dp)

        AllNavItems.forEach { screen ->
            NavItem(
                screen = screen,
                selected = screen == currentScreen,
                onNavItemClick = onNavItemClick
            )
        }
    }
}

@Composable
private fun NavItem(
    screen: Screen,
    selected: Boolean,
    onNavItemClick: (Screen) -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent
    )

    val textColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(size = 8.dp))
            .clickable(enabled = !selected) { onNavItemClick(screen) }
            .background(color = backgroundColor)
            .padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        Text(
            text = screen.name,
            color = textColor
        )
    }
}
