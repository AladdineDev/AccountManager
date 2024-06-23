
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.accountmanager.viewmodel.AccountViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountListScreen(navController: NavHostController, viewModel: AccountViewModel = viewModel()) {
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
                onClick = { navController.navigate("add_edit_account") },
                modifier = Modifier.padding(16.dp),
                content = {
                    Icon(Icons.Default.Add, contentDescription = "Add Account")
                }
            )

        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            BasicTextField(
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
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true
            )
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                val displayAccounts = if (query.text.isNotEmpty()) searchResults else accounts
                items(displayAccounts) { account ->
                    Text(text = "${account.service}: ${account.identifier}")
                }
            }
        }
    }
}
