package com.example.accountmanager.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.accountmanager.data.AppDatabase
import com.example.accountmanager.data.SettingsDao
import com.example.accountmanager.model.Account
import com.example.accountmanager.model.Settings
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application): AndroidViewModel(application) {

    private val settingsDao: SettingsDao = AppDatabase.getDatabase(application).settingsDao()

    val savedSettings: LiveData<Settings> = settingsDao.getSettings()

    fun saveSettings(settings: Settings) {
        viewModelScope.launch {
            settingsDao.saveSettings(settings)
        }
    }

}