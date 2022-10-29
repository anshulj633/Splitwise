package com.demo.splitwise.transactions.services

import com.demo.splitwise.transactions.enums.SplitType
import com.demo.splitwise.transactions.models.TransactionRequest
import com.demo.splitwise.transactions.models.UserExpensesDto
import com.demo.splitwise.transactions.models.UserShareResponseDto
import com.demo.splitwise.users.models.User
import com.demo.splitwise.users.models.UserRequest
import com.demo.splitwise.users.repositories.TransactionRepository
import com.demo.splitwise.users.services.UserBalancesService
import com.demo.splitwise.users.services.UserService
import org.springframework.stereotype.Service

@Service
class TransactionManagerService (
    val transactionRepository: TransactionRepository,
    val transactionService: TransactionService,
    val userTransactionService: UserTransactionService,
    val userService: UserService,
    val userBalancesService: UserBalancesService
) {

    fun addExpense(expenseRequest: TransactionRequest){
        validateExpenseRequest(expenseRequest)
        val paidByUser = userService.getUser(expenseRequest.paidBy!!)
        val paidForUsers = getOrCreateUsers(expenseRequest.users.map { it.userName!! })
        val splitType = SplitType.valueOf(expenseRequest.splitType!!)

        val transaction = transactionService.addTransaction(expenseRequest.paidBy, splitType, expenseRequest.amount)
        val userTransactions = userTransactionService.addUserTransactions(transaction, expenseRequest.users.map { it.convertToUserShareFunctionDto() })

        userBalancesService.updateBalances(transaction, userTransactions)
    }

    fun validateExpenseRequest(expenseRequest: TransactionRequest){
        try {
            SplitType.valueOf(expenseRequest.splitType!!)
        }
        catch (IllegalArgumentException: Exception){
            println("Please use the correct split type")
        }

    }

    fun getOrCreateUsers(userNames: List<String>): List<User>{
        val users = userService.getUsers(userNames)
        val registeredUserNames = users.map { it.userName }
        val nonRegisteredUsers = userNames.filter { it !in registeredUserNames }

        nonRegisteredUsers.forEach {
            users.add(userService.addUser(UserRequest(it)))
        }

        return users
    }

    fun getAllExpenses(userName: String): Map<String, List<UserExpensesDto>>{
        try {
            val userTransactions = userTransactionService.getAllTransactionForUser(userName)
            val transactionIds = userTransactions.map { it.transactionId }.toSet().toList()
            val transactions = transactionService.getTransactions(transactionIds)

            val userExpenses = mutableListOf<UserExpensesDto>()
            transactions.forEach { transaction ->
                val userShareResponseDtoList = userTransactionService.getAllTransactionForUser(transaction.id!!).map { UserShareResponseDto(it.userName, it.amount.unaryPlus()) }
                val userExpensesDto = UserExpensesDto(
                    transaction.paidBy,
                    transaction.amount,
                    userShareResponseDtoList,
                    splitType = transaction.splitType.name
                )

                userExpenses.add(userExpensesDto)
            }

            return mapOf("expenses" to userExpenses.toList())
        }
        catch (e: Exception){
            println(e.message)
            return mapOf("expenses" to listOf<UserExpensesDto>())
        }
    }

}