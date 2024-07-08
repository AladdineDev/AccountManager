package com.example.accountmanager.model

import com.example.accountmanager.R

enum class Service(val displayName: String) {
    APPLE("Apple"),
    FACEBOOK("Facebook"),
    GITHUB("GitHub"),
    GOOGLE("Google"),
    MICROSOFT("Microsoft"),
    TWITTER("Twitter"),
    YAHOO("Yahoo");

    fun getLogoResId(): Int {
        return when (this) {
            APPLE -> R.drawable.apple
            FACEBOOK -> R.drawable.facebook
            GITHUB -> R.drawable.github
            GOOGLE -> R.drawable.search
            MICROSOFT -> R.drawable.microsoft
            TWITTER -> R.drawable.twitter
            YAHOO -> R.drawable.yahoo
        }
    }
}