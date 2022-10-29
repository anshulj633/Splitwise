package com.demo.splitwise.strategies

import com.demo.splitwise.strategies.Split
import com.demo.splitwise.transactions.models.Transaction
import com.demo.splitwise.transactions.models.UserShareFunctionDto
import com.demo.splitwise.users.models.User

class EqualSplit(user: String) : Split(user) {
    override fun calculateAmount(transaction: Transaction, userShare: List<UserShareFunctionDto>) {
        val shareAmount = transaction.amount/userShare.size
        this.amount = if (transaction.paidBy != this.userName)
            shareAmount.unaryMinus()
        else
            shareAmount
    }
}