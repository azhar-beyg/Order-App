package com.example.orderapp

import android.preference.PreferenceManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun NavigationComposable() {
    val context = LocalContext.current
    val sp = PreferenceManager.getDefaultSharedPreferences(context)
    val firstName = sp.getString("firstName", null) // Second parameter is the default value.
    val lastName = sp.getString("lastName", null) // Second parameter is the default value.
    val email = sp.getString("email", null) // Second parameter is the default value.

    val navController = rememberNavController()
    val startDestination = if (firstName == null && lastName == null && email == null) {
        Onboarding.route
    } else {
        Home.route
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Onboarding.route) {
            Onboarding(navController = navController)
        }
        composable(Home.route) {
            Home(navController = navController)
        }
        composable(Profile.route) {
            Profile(navController = navController)
        }
    }
//    if (firstName == null && lastName == null && email == null) {
//        NavHost(
//            navController = navController,
//            startDestination = Onboarding.route
//        ) {
//            composable(Onboarding.route) {
//                Onboarding(navController = navController)
//            }
//            composable(Home.route) {
//                Home(navController = navController)
//            }
//            composable(Profile.route) {
//                Profile(navController = navController)
//            }
////            composable(Onboarding.route){
////                Onboarding(navController = navController)
////            }
//        }
//    } else {
//        NavHost(
//            navController = navController,
//            startDestination = Home.route
//        ) {
//            composable(Home.route) {
//                Home(navController = navController)
//            }
//            composable(Profile.route) {
//                Profile(navController = navController)
//            }
//            composable(Onboarding.route){
//                Onboarding(navController = navController)
//            }
//        }
//
//    }
}