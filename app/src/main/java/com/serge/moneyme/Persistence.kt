package com.serge.moneyme

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

const val AMOUNT_KEY = "amountKey"
const val MONTHS_KEY = "monthsKey"

class Persistence(context: Context) {
    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveFinanceDetails(amount: Int, months: Int) {
        preferences.edit().putInt(AMOUNT_KEY, amount).apply()
        preferences.edit().putInt(MONTHS_KEY, months).apply()
    }

    fun getAmountValue() = preferences.getInt(AMOUNT_KEY, 0)
    fun getMonthsValue() = preferences.getInt(MONTHS_KEY, 0)
}