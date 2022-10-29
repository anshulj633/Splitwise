package com.demo.splitwise.users.services

import com.demo.splitwise.transactions.models.Transaction
import com.demo.splitwise.transactions.models.UserTransaction
import com.demo.splitwise.users.models.User
import com.demo.splitwise.users.models.UserBalance
import com.demo.splitwise.users.models.UserBalanceId
import com.demo.splitwise.users.models.UserRequest
import com.demo.splitwise.users.repositories.UserBalanceRepository
import com.demo.splitwise.users.repositories.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.print.attribute.standard.JobOriginatingUserName

@Service
class UserBalancesService(
    private val userBalanceRepository: UserBalanceRepository,
    private val userService: UserService
) {

    /**
     * Update balances btw 2 users
     *
     * @param: transaction :[Transaction]
     * @param: userTransactions: [List<UserTransaction>]
     */
    fun updateBalances(transaction: Transaction, userTransactions: List<UserTransaction>) {
        userTransactions.filter { it.userName != transaction.paidBy }.forEach {
            var isPayee = false
            var userBalance = userBalanceRepository.findByIdOrNull(UserBalanceId(transaction.paidBy, it.userName))

            if (userBalance == null) {
                userBalance = userBalanceRepository.findByIdOrNull(UserBalanceId(it.userName, transaction.paidBy))
                isPayee = true
            }

            if (userBalance == null) {
                userBalance = UserBalance(transaction.paidBy, it.userName, 0.0)
                isPayee = false
            }

            userBalance.amount = if (isPayee) {
                val amountToAdd = it.amount
                userBalance.amount.plus(amountToAdd)
            } else {
                val amountToAdd = it.amount.unaryMinus()
                userBalance.amount.plus(amountToAdd)
            }

            userService.updateBalance(it.userName, it.amount)
            userBalanceRepository.save(userBalance)
        }
        userService.updateBalance(transaction.paidBy, transaction.amount)
    }

    /**
     * returns all the non zero balances with other users for given user
     *
     * @param: userName: [String]
     */
    fun getAllOutstandingBalances(userName: String): List<UserBalance> {
        return try {
            userBalanceRepository.findByPaidByOrPaidFor(userName, userName).filter { it.amount != 0.0 }
        } catch (e: Exception) {
            println(e)
            emptyList()
        }
    }
}