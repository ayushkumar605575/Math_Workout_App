package com.example.mathsworkout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mathsworkout.ui.screens.LearnScreen
import com.example.mathsworkout.ui.screens.PracticeScreen
import com.example.mathsworkout.ui.screens.ProfileScreen
import com.example.mathsworkout.ui.screens.QuizScreen
import com.example.mathsworkout.ui.theme.MathsWorkoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MathsWorkoutTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserScreen()
                }
            }
        }
    }
}


@Composable
fun NavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(navController = navController, startDestination = BottomBarScreen.Learn.route) {
        composable(route = BottomBarScreen.Learn.route) {
            LearnScreen(modifier = Modifier.padding(paddingValues))
        }
        composable(route = BottomBarScreen.Practice.route) {
            PracticeScreen(modifier = Modifier.padding(paddingValues))
        }
        composable(route = BottomBarScreen.Quiz.route) {
            QuizScreen(modifier = Modifier.padding(paddingValues))
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen(modifier = Modifier.padding(paddingValues))
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Learn,
        BottomBarScreen.Practice,
        BottomBarScreen.Quiz,
        BottomBarScreen.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        selected = currentDestination?.route == screen.route,
        alwaysShowLabel = currentDestination?.route == screen.route,
        label = { Text(screen.title, color = MaterialTheme.colorScheme.primary) },
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    )

}

@Composable
fun UserScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { paddingValues ->
        NavGraph(navController = navController, paddingValues)
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    MathsWorkoutTheme {
        UserScreen()
    }
}