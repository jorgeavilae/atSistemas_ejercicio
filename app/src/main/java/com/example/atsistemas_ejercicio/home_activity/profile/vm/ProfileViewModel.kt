package com.example.atsistemas_ejercicio.home_activity.profile.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.atsistemas_ejercicio.commons.BaseViewModel
import com.example.data.repositories.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: TransactionRepository): BaseViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _surname = MutableLiveData<String>()
    val surname: LiveData<String>
        get() = _surname

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _name.postValue(repository.getName())
            _surname.postValue(repository.getSurname())
        }
    }

    fun setName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setName(name)
        }
    }

    fun setSurname(surname: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setSurname(surname)
        }
    }
}