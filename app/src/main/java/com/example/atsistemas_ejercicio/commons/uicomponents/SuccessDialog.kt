package com.example.atsistemas_ejercicio.commons.uicomponents

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.example.atsistemas_ejercicio.R
import com.example.atsistemas_ejercicio.commons.Constants
import com.example.atsistemas_ejercicio.databinding.DialogSuccessBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SuccessDialog(
    private val mContext: Context,
    private val mBody: String
) : Dialog(mContext) {
    private lateinit var binding: DialogSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogSuccessBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.dialogOkBody.text = mBody
    }

    override fun show() {
        super.show()

        val window = window
        window?.attributes?.windowAnimations = R.style.MyDialogAnimation
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        (mContext as FragmentActivity).lifecycleScope.launch {
            delay(Constants.DIALOG_SUCCESS_SHOW)
            this@SuccessDialog.dismiss()
        }
    }
}