package com.serge.moneyme.dialog

import android.app.Dialog
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.serge.moneyme.R

class SuccessDialog(
    private val activity: AppCompatActivity
) {
    fun showDialog() {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.success_dialog)

        val dialogMessage = dialog.findViewById<TextView>(R.id.dialog_message)
        dialogMessage.text = activity.getString(R.string.success_message)

        val buttonOk = dialog.findViewById<Button>(R.id.btn_ok)
        buttonOk.text = activity.getString(R.string.dialog_ok)
        buttonOk.setOnClickListener {
            dialog.dismiss()
            activity.finish()
        }

        dialog.show()
    }
}