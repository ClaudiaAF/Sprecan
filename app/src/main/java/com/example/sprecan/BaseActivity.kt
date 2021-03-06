package com.example.sprecan

import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {

    fun showErrorSnackBar(message: String, errorMessage: Boolean){
        val snackBar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)

        val snackBarView = snackBar.view

        if(errorMessage) {
            snackBarView.setBackgroundColor(
                    ContextCompat.getColor(
                            this, R.color.design_default_color_error
                    )
                )
            } else {
            snackBarView.setBackgroundColor(
                    ContextCompat.getColor(
                            this, R.color.design_default_color_secondary
                    )
            )
        }
        snackBar.show()
    }

}