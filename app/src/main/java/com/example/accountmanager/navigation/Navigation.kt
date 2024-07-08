package com.example.accountmanager.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType.Companion.IntType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.accountmanager.ui.screen.AccountListScreen
import com.example.accountmanager.ui.screen.AddAccountScreen
import com.example.accountmanager.ui.screen.EditAccountScreen
import com.example.accountmanager.ui.screen.SettingsScreen
import com.example.accountmanager.viewmodel.SettingsViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "account_list") {
        composable("account_list") {
            AccountListScreen(navController = navController)
        }
        composable("add_account") {
            AddAccountScreen(navController = navController)
        }
        composable("settings") {
            val settingsViewModel = viewModel<SettingsViewModel>()
            SettingsScreen(navController = navController, settingsViewModel)
        }
        composable(
            route = "edit_account/{accountId}",
            arguments = listOf(navArgument("accountId") { type = IntType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val accountId = arguments.getInt("accountId")

            EditAccountScreen(navController = navController, accountId = accountId)
        }
    }
}
