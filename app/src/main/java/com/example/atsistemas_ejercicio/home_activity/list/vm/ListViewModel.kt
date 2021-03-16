package com.example.atsistemas_ejercicio.home_activity.list.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.atsistemas_ejercicio.commons.BaseViewModel
import com.example.data.models.TransactionDTO
import com.example.data.remote.ResultHandler
import com.example.data.repositories.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(private val repository: TransactionRepository) : BaseViewModel() {

    val transactionList: LiveData<List<TransactionDTO>> = repository.mTransactions

    fun fetchTransactions() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = repository.getTransactionsAndSave()) {
                is ResultHandler.Success -> {
                    showMessage(result.data)
                }
                else -> {
                    setShowError(result)
                }
            }
            _isLoading.postValue(false)
        }
    }

    fun clearList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTransactions()
        }
    }
}