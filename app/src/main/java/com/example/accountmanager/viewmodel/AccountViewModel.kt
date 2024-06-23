package com.example.accountmanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.accountmanager.data.AccountDao
import com.example.accountmanager.data.AppDatabase
import com.example.accountmanager.model.Account
import com.example.accountmanager.model.Service
import kotlinx.coroutines.launch

class AccountViewModel(application: Application) : AndroidViewModel(application) {

    private val accountDao: AccountDao = AppDatabase.getDatabase(application).accountDao()

    val accounts: LiveData<List<Account>> = accountDao.getAllAccounts()

    private val _searchResults = MutableLiveData<List<Account>>()
    val searchResults: LiveData<List<Account>> = _searchResults

    fun searchAccounts(query: String) {
        viewModelScope.launch {
            accountDao.searchAccounts(query).asFlow().collect { results ->
                _searchResults.value = results
            }
        }
    }


    fun addAccount(service: Service, identifier: String, password: String) {
        viewModelScope.launch {
            accountDao.insert(Account(service = service.displayName, identifier = identifier, password = password))
        }
    }

    fun updateAccount(account: Account) {
        viewModelScope.launch {
            accountDao.update(account)
        }
    }

    fun deleteAccount(account: Account) {
        viewModelScope.launch {
            accountDao.delete(account)
        }
    }
}
