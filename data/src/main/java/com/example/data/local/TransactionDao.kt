package com.example.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.commons.Constants.TABLE_TRANSACTIONS
import com.example.data.models.TransactionDTO

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(transactions: List<TransactionDTO>)

    @Query("SELECT * FROM `$TABLE_TRANSACTIONS`")
    fun load(): LiveData<List<TransactionDTO>>

    @Query("DELETE FROM `$TABLE_TRANSACTIONS`")
    fun deleteAll()

    @Query("SELECT * FROM `$TABLE_TRANSACTIONS` WHERE id = :transactionId")
    fun getTransactionById(transactionId: String) : TransactionDTO?
}