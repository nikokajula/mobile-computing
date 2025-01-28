package com.example.hw1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import kotlinx.serialization.Serializable

@Serializable
object Profile
@Serializable
object Conversation

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),

    ) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Conversation
    ) {
        composable<Profile> {
            ProfileScreen(onNavigateToConversation = { navController.navigate(route = Conversation, navOptions = navOptions { popUpTo<Conversation>{inclusive = true} } )})
        }
        composable<Conversation> {
            ConversationScreen(
                onNavigateToProfile = { navController.navigate(route = Profile) },
                SampleData.conversationSample
            )
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppNavHost()
        }
    }
}


