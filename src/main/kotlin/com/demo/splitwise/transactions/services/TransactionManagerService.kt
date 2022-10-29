package com.demo.splitwise.transactions.services

import com.demo.splitwise.transactions.enums.SplitType
import com.demo.splitwise.transactions.models.TransactionRequest
import com.demo.splitwise.transactions.models.UserExpensesDto
import com.demo.splitwise.transactions.models.UserShareResponseDto
import com.demo.splitwise.users.models.User
import com.demo.splitwise.users.models.UserRequest
import com.demo.splitwise.users.services.UserBalancesService
import com.demo.splitwise.users.services.UserService
import org.springframework.stereotype.Service


/**
 * This is a manager class for adding expense and getting expense
 */
@Service
class TransactionManagerService (
    val transactionService: TransactionService,
    val userTransactionService: UserTransactionService,
    val userService: UserService,
    val userBalancesService: UserBalancesService
) {

    /**
     * This function creates [Transaction], [UserTransaction] and
     * then settle balance in [UserBalance] then updates overall balance of [User]
     *
     * @param: expenseRequest:[TransactionRequest]
     */
    fun addExpense(expenseRequest: TransactionRequest){
        validateExpenseRequest(expenseRequest)
        userService.getUser(expenseRequest.paidBy!!)
        userService.createUsers(expenseRequest.users.map { it.userName!! })
        val splitType = SplitType.valueOf(expenseRequest.splitType!!)

        val transaction = transactionService.addTransaction(expenseRequest.paidBy, splitType, expenseRequest.amount)
        val userTransactions = userTransactionService.addUserTransactions(transaction, expenseRequest.users.map { it.convertToUserShareFunctionDto() })

        userBalancesService.updateBalances(transaction, userTransactions)
    }

    /**
     * validates percentage, split type
     *
     * @param: expenseRequest: [TransactionRequest]
     */
    fun validateExpenseRequest(expenseRequest: TransactionRequest){
        try {
            val splitType = SplitType.valueOf(expenseRequest.splitType!!)
            if (splitType == SplitType.Percent)
                if (expenseRequest.users.sumOf { it.sharePercentage!! } != 100.0)
                    throw IllegalArgumentException()
        }
        catch (IllegalArgumentException: Exception){
            println("Please use the correct split type")
        }

    }

    /**
     * returns all the expenses of give user
     *
     * @param: userName
     *
     * @return: [Map<String, List<UserExpensesDto>>]
     */
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