package com.example.atsistemas_ejercicio.home_activity.detail.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.atsistemas_ejercicio.commons.BaseViewModel
import com.example.atsistemas_ejercicio.commons.Constants
import com.example.data.models.TransactionDTO
import com.example.data.repositories.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DDetailViewModel(private val repository: TransactionRepository): BaseViewModel() {

    private val _transaction = MutableLiveData<TransactionDTO>()
    val transaction: LiveData<TransactionDTO>
        get() = _transaction

    fun fetchTransaction(transactionId: String) {
        if (transactionId.isNotEmpty()) {
            _isLoading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                val result = repository.getTransactionById(transactionId)

                if (result == null) {
                    setShowErrorString(Constants.TRANSACTION_NOT_FOUND)
                } else {
                    _transaction.postValue(result!!)
                }
                _isLoading.postValue(false)
            }
        } else {
            setShowErrorString(Constants.TRANSACTION_NOT_FOUND)
        }
    }
}