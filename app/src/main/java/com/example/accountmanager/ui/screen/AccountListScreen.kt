package com.example.accountmanager.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.accountmanager.model.Account
import com.example.accountmanager.viewmodel.AccountViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountListScreen(
    navController: NavHostController,
    viewModel: AccountViewModel = viewModel()
) {
    val accounts by viewModel.accounts.observeAsState(emptyList())
    val searchResults by viewModel.searchResults.observeAsState(emptyList())

    var query by remember { mutableStateOf(TextFieldValue("")) }
    var lastQuery by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Account List") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_account") },
                modifier = Modifier.padding(16.dp),
                content = {
                    Icon(Icons.Default.Add, contentDescription = "Add Account")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            TextField(
                value = query,
                onValueChange = {
                    query = it
                    val newQuery = it.text
                    if (newQuery == query.text) {
                        lastQuery = newQuery
                        viewModel.searchAccounts(newQuery)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { Text("Search Accounts") },
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { query = TextFieldValue("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear Search")
                    }
                },
            )
            Box(Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                val displayAccounts = if (query.text.isNotEmpty()) searchResults else accounts
                items(displayAccounts) { account ->
                    AccountListItem(
                        account = account,
                        onEditClick = {
                            navController.navigate("edit_account/${account.id}")
                        },
                        onDeleteClick = {
                            viewModel.deleteAccount(account)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    showDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("Delete Account") },
            text = { Text("Are you sure you want to delete this account?") },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm()
                        onDismiss()
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(
                    onClick = { onDismiss() }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountListItem(
    account: Account,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            ListItem(
                headlineContent = { Text(text = account.service.displayName) },
                supportingContent = { Text(text = account.email) },
                trailingContent = {
                    Row {
                        IconButton(onClick = { onEditClick() }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = { showDialog = true }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            )
            DeleteConfirmationDialog(
                showDialog = showDialog,
                onConfirm = onDeleteClick,
                onDismiss = { showDialog = false }
            )
        }
    }
}
