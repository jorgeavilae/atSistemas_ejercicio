package com.example.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.data.commons.BaseRepository
import com.example.data.commons.Constants
import com.example.data.local.BankDatabase
import com.example.data.models.TransactionDTO
import com.example.data.remote.ITransactionAPI
import com.example.data.remote.ResultHandler
import com.example.data.utils.TransactionsUtil

class TransactionRepository(
    private val context: Context,
    private val api: ITransactionAPI,
    private val bankDB: BankDatabase
) :
    BaseRepository() {

    val mTransactions: LiveData<List<TransactionDTO>> by lazy {
        bankDB.transactionDao().load()
    }

    //API
    suspend fun getTransactionsAndSave(): ResultHandler<String> {
        //Call to API and save in Room
        return when (val result = safeApiCall(call = { api.getTransactions() })) {
            is ResultHandler.Success -> {
                //Sort the list
                result.data.let {

                    var sortedList = result.data.toMutableList()
                        // .filter { utils.filter SINGLE Transaction }
                        // .distinctBy { utils.isduplicateid in collection }
                        .sortedWith(compareByDescending { it.date })
                    sortedList = TransactionsUtil.filterTransactions(sortedList)

                    //Save data in Room
                    bankDB.transactionDao().save(sortedList)
                }
                //It is not necessary to return nothing, magic is done with liveData in Room
                ResultHandler.Success("Successful update")
            }
            is ResultHandler.GenericError -> result
            is ResultHandler.HttpError -> result
            is ResultHandler.NetworkError -> result
        }
    }

    //Database
//    suspend fun saveTransactions(transactions: List<TransactionDTO>) {
//        bankDB.transactionDao().save(transactions)
//    }

    fun getTransactionById(transactionId: String): TransactionDTO? =
        bankDB.transactionDao().getTransactionById(transactionId)

    fun deleteTransactions() {
        bankDB.transactionDao().deleteAll()
    }

    //SharedPreferences
    fun getName(): String {
        val sharedPref = context.getSharedPreferences(
            Constants.SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE
        )
        return sharedPref.getString(Constants.PREFERENCES_NAME_KEY, "") ?: ""
    }

    fun setName(name: String) {
        val sharedPref =
            context.getSharedPreferences(Constants.SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(Constants.PREFERENCES_NAME_KEY, name)
            commit()
        }
    }

    fun getSurname(): String {
        val sharedPref = context.getSharedPreferences(
            Constants.SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE
        )
        return sharedPref.getString(Constants.PREFERENCES_SURNAME_KEY, "") ?: ""
    }

    fun setSurname(surname: String) {
        val sharedPref =
            context.getSharedPreferences(Constants.SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(Constants.PREFERENCES_SURNAME_KEY, surname)
            commit()
        }
    }
}