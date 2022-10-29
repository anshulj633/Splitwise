package com.demo.splitwise.users.repositories

import com.demo.splitwise.transactions.models.UserTransaction
import com.demo.splitwise.transactions.models.UserTransactionId
import org.springframework.data.repository.CrudRepository
import javax.print.attribute.standard.JobOriginatingUserName


interface UserTransactionRepository: CrudRepository<UserTransaction, UserTransactionId>{
    fun findAllByUserName(userName: String): List<UserTransaction>
    fun findAllByTransactionId(transactionId: Int): List<UserTransaction>
}