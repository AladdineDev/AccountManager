package com.example.accountmanager.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.accountmanager.model.Account
import com.example.accountmanager.model.Settings

@Dao
interface SettingsDao {

    @Query("SELECT * FROM settings LIMIT 1")
    fun getSettings(): LiveData<Settings>

    @Upsert
    fun saveSettings(settings: Settings)

}