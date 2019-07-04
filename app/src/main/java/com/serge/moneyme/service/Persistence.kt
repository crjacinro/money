package com.serge.moneyme.service

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

const val AMOUNT_KEY = "amountKey"
const val MONTHS_KEY = "monthsKey"
const val EMAIL_KEY = "emailKey"
const val NAME_KEY = "nameKey"

class Persistence(context: Context) {
    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveFinanceDetails(amount: Float, months: Float) {
        preferences.edit().putFloat(AMOUNT_KEY, amount).apply()
        preferences.edit().putFloat(MONTHS_KEY, months).apply()
    }

    fun saveUserEmail(email: String) {
        preferences.edit().putString(EMAIL_KEY, email).apply()
    }

    fun saveDisplayName(name: String) {
        preferences.edit().putString(NAME_KEY, name).apply()
    }

    fun clear() {
        preferences.edit().clear().apply()
    }

    fun getAmountValue() = preferences.getFloat(AMOUNT_KEY, 0.0F)
    fun getMonthsValue() = preferences.getFloat(MONTHS_KEY, 0.0F)
    fun getEmailValue() = preferences.getString(EMAIL_KEY, "")
    fun getNameValue() = preferences.getString(NAME_KEY, "")
}