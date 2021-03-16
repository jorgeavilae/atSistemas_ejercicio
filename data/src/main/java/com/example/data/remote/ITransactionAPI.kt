package com.example.data.remote

import com.example.data.models.TransactionDTO
import retrofit2.Response
import retrofit2.http.GET

interface ITransactionAPI {
    @GET("/transactions.json")
    suspend fun getTransactions(): Response<List<TransactionDTO>>
}