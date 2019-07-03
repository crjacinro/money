package com.serge.moneyme

import android.app.Dialog
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ConfirmDialog(
    private val activity: AppCompatActivity
) {

    fun showDialog() {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog)

        val dialogMessage = dialog.findViewById<TextView>(R.id.dialog_message)
        dialogMessage.text = activity.getString(R.string.dialog_message)

        val buttonYes = dialog.findViewById<Button>(R.id.btn_yes)
        buttonYes.text = activity.getString(R.string.dialog_yes)
        buttonYes.setOnClickListener { dialog.dismiss() }

        val buttonNo = dialog.findViewById<Button>(R.id.btn_no)
        buttonNo.text = activity.getString(R.string.dialog_no)
        buttonNo.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }
}