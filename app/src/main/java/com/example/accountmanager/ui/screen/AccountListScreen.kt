import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = { Text("Search Accounts") },
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { query = TextFieldValue("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear Search")
                    }
                },
            )
            Box(Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
                val displayAccounts = if (query.text.isNotEmpty()) searchResults else accounts
                items(displayAccounts) { account ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        ListItem(
                            headlineContent = { Text(text = account.service) },
                            supportingContent = { Text(text = account.identifier) },
                        )
                    }
                }
            }
        }
    }
}
