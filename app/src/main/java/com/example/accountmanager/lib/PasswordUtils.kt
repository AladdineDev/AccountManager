package com.example.accountmanager.lib

import com.example.accountmanager.model.LetterCase
import com.example.accountmanager.model.Settings
import java.util.Locale

object PasswordUtils {

    fun generatePassword(settings: Settings): String {
        val letters = "abcdefghijklmnopqrstuvwxyz"
        val numbers = "0123456789"
        val specialChars = "!@#\$%^&*"
        var allowedChars = when (settings.letterCase) {
            LetterCase.LOWERCASE -> letters
            LetterCase.UPPERCASE -> letters.uppercase(Locale.ROOT)
            LetterCase.MIXED -> letters + letters.uppercase(Locale.ROOT)
        }
        if (settings.includeNumbers) allowedChars += numbers
        if (settings.includeSpecialChars) allowedChars += specialChars

        val password = StringBuilder(settings.passwordLength)
        for (i in 0 until settings.passwordLength) {
            val randomIndex = (allowedChars.indices).random()
            password.append(allowedChars[randomIndex])
        }
        return password.toString()
    }
}