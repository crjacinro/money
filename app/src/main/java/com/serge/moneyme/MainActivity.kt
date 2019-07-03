package com.serge.moneyme

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat


const val MINIMUM_AMOUNT = 2000.00
const val MAXIMUM_AMOUNT = 15000.00
const val AMOUNT_SLOPE = ((MAXIMUM_AMOUNT - MINIMUM_AMOUNT) / 100)

const val MINIMUM_MONTHS = 3
const val MAXIMUM_MONTHS = 36
const val MONTHS_SLOPE = ((MAXIMUM_MONTHS - MINIMUM_MONTHS).toDouble() / 100)

class MainActivity : AppCompatActivity() {

    private val amountFormat = DecimalFormat("$ #,###")
    private val monthsFormat = DecimalFormat("# months")

    private var currentAmountValue = 0
    private var currentMonthsValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeView()
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
            val dialog = ConfirmDialog(this)

            dialog.showDialog()
        }
    }
}

