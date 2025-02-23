package com.example.accountmanager.ui.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.accountmanager.R
import com.example.accountmanager.lib.PasswordUtils
import com.example.accountmanager.model.Service
import com.example.accountmanager.model.Settings
import com.example.accountmanager.viewmodel.AccountViewModel
import com.example.accountmanager.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccountScreen(
    navController: NavHostController,
    accountViewModel: AccountViewModel = viewModel(),
    settingsViewModel: SettingsViewModel = viewModel()
) {
    val passwordSettings by settingsViewModel.savedSettings.observeAsState(Settings())

    var service by remember { mutableStateOf(Service.entries.first()) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add_account)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.LightGray)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    TextField(
                        value = service.displayName,
                        onValueChange = {},
                        readOnly = true,
                        enabled = false,
                        colors = TextFieldDefaults.colors(
                            disabledTextColor = Color.Black,
                            disabledLabelColor = Color.Black,
                            disabledTrailingIconColor = Color.Black,
                            disabledSupportingTextColor = Color.Black
                        ),
                        label = { Text(stringResource(R.string.service)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .clickable { expanded = true },
                        leadingIcon = {
                            Image(
                                modifier = Modifier.size(28.dp),
                                painter = painterResource(service.getLogoResId()),
                                contentDescription = service.displayName
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { expanded = true }) {
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    contentDescription = stringResource(R.string.dropdown_arrow)
                                )
                            }
                        },
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Service.entries.forEach { selectedService ->
                            DropdownMenuItem(
                                text = {
                                    Row (
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        Image(
                                            modifier = Modifier.size(28.dp),
                                            painter = painterResource(selectedService.getLogoResId()),
                                            contentDescription = selectedService.displayName
                                        )
                                        Text(selectedService.displayName)
                                    }
                                },
                                onClick = {
                                    service = selectedService
                                    expanded = false
                                }
                            )
                        }
                    }

                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(stringResource(R.string.email)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Email,
                            autoCorrect = false
                        )
                    )
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(stringResource(R.string.password)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Password,
                            autoCorrect = false
                        ),
                        trailingIcon = {
                            val icon =
                                if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            Row {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = if (passwordVisible) stringResource(R.string.hide_password) else stringResource(
                                            R.string.show_password
                                        )
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        password = PasswordUtils.generatePassword(passwordSettings)
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Autorenew,
                                        contentDescription = "Generate password"
                                    )
                                }
                            }
                        }
                    )
                    Button(
                        onClick = {
                            if (email.isEmpty() || password.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Email and password are required",
                                    Toast.LENGTH_LONG
                                ).show()
                                return@Button
                            }

                            accountViewModel.addAccount(service, email, password)
                            navController.popBackStack()
                            Toast.makeText(
                                context,
                                R.string.account_created_successfully,
                                Toast.LENGTH_LONG
                            ).show()

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Text(text = stringResource(R.string.save))
                    }
                }
            }
        }
    }
}
