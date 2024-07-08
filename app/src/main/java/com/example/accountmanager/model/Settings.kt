package com.example.accountmanager.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.accountmanager.R

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey val id: Int = 1,
    val passwordLength: Int = 12,
    val includeNumbers: Boolean = true,
    val includeSpecialChars: Boolean = false,
    val letterCase: LetterCase = LetterCase.MIXED
)

enum class LetterCase {
    LOWERCASE,
    UPPERCASE,
    MIXED;

    fun stringResId(): String {
        return when (this) {
            // LOWERCASE -> R.string.lowercase
            LOWERCASE -> "lowercase"
//            UPPERCASE -> R.string.uppercase
            UPPERCASE -> "uppercase"
//            MIXED -> R.string.mixed
            MIXED -> "mixed"
        }
    }
}