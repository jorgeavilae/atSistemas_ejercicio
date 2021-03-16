package com.example.atsistemas_ejercicio.commons

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.atsistemas_ejercicio.commons.uicomponents.ErrorDialog
import com.example.atsistemas_ejercicio.commons.uicomponents.SuccessDialog

abstract class BaseFragment: Fragment() {

    var errorDialog : ErrorDialog? = null
    var successDialog : SuccessDialog? = null

    abstract fun loadObservers()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        errorDialog?.dismiss()
        successDialog?.dismiss()
    }
}