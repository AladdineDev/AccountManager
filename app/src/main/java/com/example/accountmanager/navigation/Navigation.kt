package com.example.accountmanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.accountmanager.ui.screen.AccountListScreen
import com.example.accountmanager.ui.screen.AddEditAccountScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "account_list") {
        composable("account_list") { AccountListScreen(navController) }
        composable("add_edit_account") { AddEditAccountScreen(navController) }
    }
}
