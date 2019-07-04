package com.serge.moneyme

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_quote.*
import java.text.DecimalFormat

class QuoteActivity : AppCompatActivity() {

    private val amountFormat = DecimalFormat("$ #,###")
    private val monthsFormat = DecimalFormat("# months")

    private lateinit var persistence: Persistence

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote)

        persistence = Persistence(this)

        initialize()
    }

    private fun initialize() {
        initFinanceDetailsButton()
        initFinanceAmount()
        initRepayments()
    }

    private fun initFinanceDetailsButton() {
        finance_details_button.setOnClickListener {
            finish()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun initFinanceAmount() {
        val amount = amountFormat.format(persistence.getAmountValue())
        val months = monthsFormat.format(persistence.getMonthsValue())
        val financeAmount = "$amount over $months"

        finance_amount_value.text = financeAmount
    }

    private fun initRepayments() {
        // repayments_value.text = financeAmount
    }
}
