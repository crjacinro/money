package com.serge.moneyme

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

const val AMOUNT_KEY = "amountKey"
const val MONTHS_KEY = "monthsKey"
const val EMAIL_KEY = "emailKey"
const val NAME_KEY = "nameKey"

class Persistence(context: Context) {
    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveFinanceDetails(amount: Int, months: Int) {
        preferences.edit().putInt(AMOUNT_KEY, amount).apply()
        preferences.edit().putInt(MONTHS_KEY, months).apply()
    }

    fun saveUserEmail(email: String) {
        preferences.edit().putString(EMAIL_KEY, email).apply()
    }

    fun saveDisplayName(name: String) {
        preferences.edit().putString(NAME_KEY, name).apply()
    }

    fun getAmountValue() = preferences.getInt(AMOUNT_KEY, 0)
    fun getMonthsValue() = preferences.getInt(MONTHS_KEY, 0)
    fun getEmailValue() = preferences.getString(EMAIL_KEY, "")
    fun getNameValue() = preferences.getString(NAME_KEY, "")
}