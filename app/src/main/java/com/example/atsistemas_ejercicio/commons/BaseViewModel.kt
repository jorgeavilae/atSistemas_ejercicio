package com.example.atsistemas_ejercicio.commons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.atsistemas_ejercicio.utils.SingleLiveEvent
import com.example.data.remote.ResultHandler

abstract class BaseViewModel: ViewModel() {

    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _showMessage = SingleLiveEvent<String>()
    val showMessage: LiveData<String>
        get() = _showMessage

    private val _showError = SingleLiveEvent<String>()
    val showError: LiveData<String>
        get() = _showError

    protected fun showMessage(text: String){
        _showMessage.postValue(text)
    }

    protected fun setShowError(resultHandler: ResultHandler<*>) {
        when(resultHandler) {
            is ResultHandler.HttpError ->
                _showError.postValue(resultHandler.code.toString())
            is ResultHandler.GenericError ->
                _showError.postValue(resultHandler.message.toString())
            is ResultHandler.NetworkError ->
                _showError.postValue(Constants.NETWORK_ERROR)
            else ->
                _showError.postValue(Constants.NETWORK_ERROR)
        }
    }

    protected fun setShowErrorString(errorMessage: String) {
        _showError.postValue(errorMessage)
    }
}