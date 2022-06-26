package com.example.githubuserlist.bindingAdapters

/**
 * Adapters to handle case not supported by data biding
 */

// Edit an integer to a String
fun editIntToString(value: Int?) : String {
    return value.toString()
}

// Handling empty or null String
fun handleString(value: String?) : String {
    return if (value.isNullOrEmpty()) {
        ("Unknown")
    } else {
        value
    }
}