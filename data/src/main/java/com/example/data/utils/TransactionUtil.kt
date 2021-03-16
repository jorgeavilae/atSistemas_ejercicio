package com.example.data.utils

import com.example.data.models.TransactionDTO

/**
 * Filter the collection for invalid dates and fill the total property
 * Finally will work in the view with our new filter collection
 */
object TransactionsUtil {

    fun filterTransactions(collection: List<TransactionDTO>): List<TransactionDTO> {
        val listVo = ArrayList<TransactionDTO>()
        collection.forEach { t ->
            validateDate(t.date)?.let {
                t.date = it
                if (!isDuplicatedId(t, collection)) {
                    val doubleOutput: Double
                    t.fee?.let {
                        doubleOutput = t.amount.toDouble() + t.fee.toDouble()
                        t.total = doubleOutput.toString()
                    }.run {
                        t.total = t.amount
                    }
                    listVo.add(t)
                }
            }
        }
        return listVo
    }

    private fun isDuplicatedId(transaction: TransactionDTO, list: List<TransactionDTO>): Boolean {
        return list.count { it.id == transaction.id } > 1
                && list.indexOf(transaction) > list.indexOf(list.find { it.id == transaction.id })
    }
}