package com.serge.moneyme

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.serge.moneyme.dialog.SuccessDialog
import com.serge.moneyme.service.PMTHelper.Companion.calculatePMT
import com.serge.moneyme.service.Persistence
import kotlinx.android.synthetic.main.activity_quote.*
import java.text.DecimalFormat

const val INTEREST_RATE = 0.02
const val PAYMENT_PERIODS = 12

class QuoteActivity : AppCompatActivity() {

    private val amountFormat = DecimalFormat("$ #,###")
    private val monthsFormat = DecimalFormat("# months")
    private val repaymentFormat = DecimalFormat("$ #.## monthly")

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
        initPersonalInfo()
        initButtons()
    }

    private fun initPersonalInfo() {
        name_value.setText(persistence.getNameValue())
        email_value.setText(persistence.getEmailValue())
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
        val periodicInterest = INTEREST_RATE / PAYMENT_PERIODS
        val amount = persistence.getAmountValue().toDouble()
        val periods = persistence.getMonthsValue().toDouble()

        val pmt = calculatePMT(periodicInterest, periods, amount)

        repayments_value.text = repaymentFormat.format(pmt)
    }

    private fun initButtons() {
        apply_now.setOnClickListener {
            SuccessDialog(this).showDialog()
        }
    }
}
