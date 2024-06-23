package com.example.accountmanager.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.accountmanager.model.Service
import com.example.accountmanager.viewmodel.AccountViewModel

@Composable
fun AddEditAccountScreen(navController: NavHostController, viewModel: AccountViewModel = viewModel()) {
    var service by remember { mutableStateOf(Service.MICROSOFT) }
    var identifier by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            // Dropdown Menu for Service Selection
            Column {
                TextField(
                    value = service.displayName,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Service") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
,                    trailingIcon = {
                        IconButton(onClick = { expanded = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown arrow")
                        }
                    }
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Service.entries.forEach { selectedService ->
                        DropdownMenuItem(
                            text = { Text(selectedService.displayName) },
                            onClick = {
                                service = selectedService
                                expanded = false
                            }
                        )
                    }
                }
            }

            TextField(
                value = identifier,
                onValueChange = { identifier = it },
                label = { Text("Identifier") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    viewModel.addAccount(service, identifier, password)
                    navController.popBackStack()
                }) {
                    Text(text = "Save")
                }
                Button(onClick = { navController.popBackStack() }) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}
