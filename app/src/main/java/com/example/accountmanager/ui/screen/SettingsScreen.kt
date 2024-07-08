package com.example.accountmanager.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.accountmanager.lib.PasswordUtils
import com.example.accountmanager.model.LetterCase
import com.example.accountmanager.model.Settings
import com.example.accountmanager.viewmodel.SettingsViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = viewModel()
) {
    val savedSettings by settingsViewModel.savedSettings.observeAsState(Settings())

    var passwordLength by remember { mutableIntStateOf(savedSettings.passwordLength) }
    var includeNumbers by remember { mutableStateOf(savedSettings.includeNumbers) }
    var includeSpecialChars by remember { mutableStateOf(savedSettings.includeSpecialChars) }
    var letterCase by remember { mutableStateOf(savedSettings.letterCase) }

    LaunchedEffect(savedSettings) {
        passwordLength = savedSettings.passwordLength
        includeNumbers = savedSettings.includeNumbers
        includeSpecialChars = savedSettings.includeSpecialChars
        letterCase = savedSettings.letterCase
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopAppBar(
            title = { Text("Add Account") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text("Password Length: ${passwordLength.toInt()}")
            Slider(
                value = passwordLength.toFloat(),
                onValueChange = { passwordLength = it.roundToInt() },
                valueRange = 6f..24f,
                steps = 18
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Include Numbers")
                Switch(
                    checked = includeNumbers,
                    onCheckedChange = { includeNumbers = it }
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Include Special Characters")
                Switch(
                    checked = includeSpecialChars,
                    onCheckedChange = { includeSpecialChars = it }
                )
            }

            var isDropdownExpanded by remember { mutableStateOf(false) }
            Row(
                modifier = Modifier.clickable {
                    isDropdownExpanded = !isDropdownExpanded
                }
            ) {
                Text("Letter Case : $letterCase")
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown arrow")
            }
            DropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false },
            ) {
                LetterCase.entries.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.stringResId()) },
                        onClick = {
                            letterCase = option
                            isDropdownExpanded = false
                        }
                    )
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    settingsViewModel.saveSettings(
                        Settings(
                            passwordLength = passwordLength,
                            includeNumbers = includeNumbers,
                            includeSpecialChars = includeSpecialChars,
                            letterCase = letterCase
                        )
                    )
                }
            ) {
                Text("Save settings")
            }

        }
    }
}