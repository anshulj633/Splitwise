package com.demo.splitwise.transactions.services

import com.demo.splitwise.transactions.enums.SplitType
import com.demo.splitwise.transactions.models.Transaction
import com.demo.splitwise.users.repositories.TransactionRepository
import org.springframework.stereotype.Service

@Service
class TransactionService(
    val transactionRepository: TransactionRepository
){

    fun addTransaction(paidByUser: String, splitType: SplitType, amount: Double): Transaction{
        val transaction = Transaction(
            paidByUser,
            amount,
            splitType
        )
        transactionRepository.save(transaction)

        return transaction
    }

    fun getTransactions(transactionIds: List<Int>): List<Transaction>{
        return transactionRepository.findAllById(transactionIds) as List<Transaction>
    }

}