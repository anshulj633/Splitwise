package com.demo.splitwise.strategies

import com.demo.splitwise.transactions.models.Transaction
import com.demo.splitwise.transactions.models.UserShareFunctionDto

class PercentSplit(user: String) : Split(user) {
    override fun calculateAmount(transaction: Transaction, userShare: List<UserShareFunctionDto>) {
        val userPercent = userShare.find { it.userName == this.userName }!!.sharePercentage!!
        val shareAmount = transaction.amount * (userPercent/100.0)

        this.amount = if (transaction.paidBy != this.userName)
            shareAmount.unaryMinus()
        else
            shareAmount
    }
}