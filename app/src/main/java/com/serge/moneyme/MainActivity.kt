package com.serge.moneyme

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat


const val MINIMUM_AMOUNT = 2000.00
const val MAXIMUM_AMOUNT = 15000.00
const val AMOUNT_SLOPE = ((MAXIMUM_AMOUNT - MINIMUM_AMOUNT) / 100)

const val MINIMUM_MONTHS = 3
const val MAXIMUM_MONTHS = 36
const val MONTHS_SLOPE = ((MAXIMUM_MONTHS - MINIMUM_MONTHS).toDouble() / 100)

const val AUTH_REQUEST_CODE = 99

class MainActivity : AppCompatActivity() {

    private val amountFormat = DecimalFormat("$ #,###")
    private val monthsFormat = DecimalFormat("# months")

    private var currentAmountValue = MINIMUM_AMOUNT.toInt()
    private var currentMonthsValue = MINIMUM_MONTHS
    private lateinit var persistence: Persistence

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeView()
        persistence = Persistence(this)
        persistence.clear()
    }

    private fun initializeView() {
        initLabels()
        initSeekBar()
        initButtons()
    }

    private fun initLabels() {
        amount_minimum.text = amountFormat.format(MINIMUM_AMOUNT)
        amount_maximum.text = amountFormat.format(MAXIMUM_AMOUNT)

        months_minimum.text = monthsFormat.format(MINIMUM_MONTHS)
        months_maximum.text = monthsFormat.format(MAXIMUM_MONTHS)

        amount_value.text = amountFormat.format(MINIMUM_AMOUNT)
    }

    private fun initSeekBar() {
        amount_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val value = (AMOUNT_SLOPE * progress) + MINIMUM_AMOUNT
                val x = amount_bar.thumb.bounds.left + 50.0F

                amount_value.text = amountFormat.format(value)
                amount_value.x = x

                currentAmountValue = value.toInt()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) { /* No operation */
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) { /* No operation  */
            }
        })

        months_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val value = (MONTHS_SLOPE * progress) + MINIMUM_MONTHS
                val x = months_bar.thumb.bounds.left + 50.0F

                months_value.text = monthsFormat.format(value)
                months_value.x = x

                currentMonthsValue = value.toInt()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) { /* No operation */
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) { /* No operation  */
            }
        })
    }

    private fun initButtons() {
        calculate_quote.setOnClickListener {
            ConfirmDialog(this, ::onYesSelected, ::onNoSelected).showDialog()
        }
    }

    private fun onYesSelected() {
        persistence.saveFinanceDetails(currentAmountValue, currentMonthsValue)
        launchAuthUi()
    }

    private fun onNoSelected() {
        persistence.saveFinanceDetails(currentAmountValue, currentMonthsValue)
        navigateToQuotesDetails()
    }

    private fun launchAuthUi() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        ActivityCompat.startActivityForResult(
            this,
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), AUTH_REQUEST_CODE,
            null
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AUTH_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                onLoginSuccess()
            } else {
                notify("Unable to login successfully")
            }
        }
    }

    private fun onLoginSuccess() {
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email.orEmpty()
        val displayName = user?.displayName.orEmpty()

        persistence.saveUserEmail(email)
        persistence.saveDisplayName(displayName)
        persistence.saveFinanceDetails(currentAmountValue, currentMonthsValue)

        navigateToQuotesDetails()
    }

    private fun navigateToQuotesDetails() {
        Intent(this, QuoteActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun notify(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

