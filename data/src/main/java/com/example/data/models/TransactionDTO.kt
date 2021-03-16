package com.example.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.commons.Constants

@Entity(tableName = Constants.TABLE_TRANSACTIONS)
data class TransactionDTO(
    @PrimaryKey
    val id: String,
    var date: String,
    val amount: String,
    val description: String?,
    val fee: String?,
    var total: String?
)