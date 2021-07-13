package com.justaccounts.mindbrowseandroidtest.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog

class Utils {
    companion object{
        lateinit var loadingDialog: ProgressBar

        fun displayAlert(
            context: Context?, title: String, message: String,
            cancellable: (Any, Any) -> Unit
        ) {
            val builder = AlertDialog.Builder(
                context!!
            )
                .setTitle(title)
                .setMessage(message)
            builder.setCancelable(false)
            builder.setPositiveButton("Ok", cancellable)
            builder.setNegativeButton("Cancel",null  )
            builder
                .create()
                .show()
        }
    }
}