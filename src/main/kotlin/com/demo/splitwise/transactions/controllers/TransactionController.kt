package com.demo.splitwise.transactions.controllers

import com.demo.splitwise.transactions.models.TransactionRequest
import com.demo.splitwise.transactions.models.UserExpensesDto
import com.demo.splitwise.transactions.services.TransactionManagerService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(path = ["/transaction"])
class TransactionController(
    val transactionManagerService: TransactionManagerService
) {

    @PostMapping("")
    fun addExpense(@Valid @RequestBody expenseRequest: TransactionRequest){
        try {
            transactionManagerService.addExpense(expenseRequest)
        }
        catch (e: Exception){
            println(e)
        }
    }

    @GetMapping("/{userName}")
    fun getFun(@PathVariable userName: String): Map<String, List<UserExpensesDto>>{
        return transactionManagerService.getAllExpenses(userName)
    }
}