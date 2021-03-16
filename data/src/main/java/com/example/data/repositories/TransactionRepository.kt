package com.example.data.repositories

import androidx.lifecycle.LiveData
import com.example.data.commons.BaseRepository
import com.example.data.local.BankDatabase
import com.example.data.models.TransactionDTO
import com.example.data.remote.ITransactionAPI
import com.example.data.remote.ResultHandler
import com.example.data.utils.TransactionsUtil
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class TransactionRepository(private val api: ITransactionAPI, private val bankDB: BankDatabase) :
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

    suspend fun getTransactionById(transactionId: String) : TransactionDTO? =
        bankDB.transactionDao().getTransactionById(transactionId)

    suspend fun deleteTransactions() {
        bankDB.transactionDao().deleteAll()
    }
}