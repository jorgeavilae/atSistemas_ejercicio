package com.example.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import com.example.data.commons.BaseRepository
import com.example.data.commons.Constants
import com.example.data.local.BankDatabase
import com.example.data.models.TransactionDTO
import com.example.data.remote.ITransactionAPI
import com.example.data.remote.ResultHandler
import com.example.data.utils.TransactionsUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.DATASTORE_NAME)

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

    // DataStore
    private val namePreferencesFlow: Flow<String> = context.dataStore.data
        .catch { exception ->
            // it throws an IOException when an error is encountered when reading data
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.map { preferences ->
            val nameKey = stringPreferencesKey(Constants.PREFERENCES_NAME_KEY)
            preferences[nameKey] ?: ""
        }

    suspend fun getName() : String = namePreferencesFlow.first()

    suspend fun setName(name: String) {
        context.dataStore.edit { preferences ->
            val nameKey = stringPreferencesKey(Constants.PREFERENCES_NAME_KEY)
            preferences[nameKey] = name
        }
    }

    private val surnamePreferencesFlow: Flow<String> = context.dataStore.data
        .catch { exception ->
            // it throws an IOException when an error is encountered when reading data
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.map { preferences ->
            val surnameKey = stringPreferencesKey(Constants.PREFERENCES_SURNAME_KEY)
            preferences[surnameKey] ?: ""
        }

    suspend fun getSurname() : String = surnamePreferencesFlow.first()

    suspend fun setSurname(surname: String) {
        context.dataStore.edit { preferences ->
            val surnameKey = stringPreferencesKey(Constants.PREFERENCES_SURNAME_KEY)
            preferences[surnameKey] = surname
        }
    }
}