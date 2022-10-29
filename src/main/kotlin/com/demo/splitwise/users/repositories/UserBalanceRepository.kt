package com.demo.splitwise.users.repositories

import com.demo.splitwise.users.models.User
import com.demo.splitwise.users.models.UserBalance
import com.demo.splitwise.users.models.UserBalanceId
import org.springframework.data.repository.CrudRepository

interface UserBalanceRepository: CrudRepository<UserBalance, UserBalanceId>{
    fun findByPaidByOrPaidFor(paidBy: String, paidFor: String): List<UserBalance>
}