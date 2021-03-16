package com.example.atsistemas_ejercicio.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.models.TransactionDTO

class SharedTransactionVM: ViewModel() {

    private var _transaction = MutableLiveData<TransactionDTO>()

    val transaction: LiveData<TransactionDTO>
        get() = _transaction

    fun setTransaction(transaction: TransactionDTO){
        _transaction.value = transaction
    }
}