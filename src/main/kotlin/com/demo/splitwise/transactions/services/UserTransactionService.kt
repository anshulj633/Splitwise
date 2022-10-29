package com.demo.splitwise.transactions.services

import com.demo.splitwise.strategies.SplitFactory
import com.demo.splitwise.transactions.models.Transaction
import com.demo.splitwise.transactions.models.UserShareFunctionDto
import com.demo.splitwise.transactions.models.UserTransaction
import com.demo.splitwise.transactions.models.UserTransactionId
import com.demo.splitwise.users.repositories.UserTransactionRepository
import org.springframework.stereotype.Service

@Service
class UserTransactionService(
    val userTransactionRepository: UserTransactionRepository
) {

    /**
     * add all [UserTransaction] for all involved users in an expense
     */
    fun addUserTransactions(transaction: Transaction, userShareDtos: List<UserShareFunctionDto>): List<UserTransaction>{
        val userTransactions = mutableListOf<UserTransaction>()
        userShareDtos.forEach {
            val split = SplitFactory.getSplitTypeObject(transaction.splitType, it.userName)
            split.calculateAmount(transaction, userShareDtos)

            val userTransaction = UserTransaction(transaction.id!!, split.userName, split.amount)
            userTransactions.add(userTransaction)
            userTransactionRepository.save(userTransaction)
        }

        userTransactionRepository.saveAll(userTransactions)

        return userTransactions
    }

    fun getAllTransactionForUser(userName: String): List<UserTransaction>{
        return userTransactionRepository.findAllByUserName(userName)
    }

    fun getAllTransactionForUser(transactionId: Int): List<UserTransaction>{
        return userTransactionRepository.findAllByTransactionId(transactionId)
    }
}