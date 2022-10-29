package com.demo.splitwise.users.repositories

import com.demo.splitwise.transactions.models.Transaction
import com.demo.splitwise.users.models.User
import org.springframework.data.repository.CrudRepository

interface TransactionRepository: CrudRepository<Transaction, Int>