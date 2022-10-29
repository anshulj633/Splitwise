package com.demo.splitwise.strategies

import com.demo.splitwise.transactions.models.Transaction
import com.demo.splitwise.transactions.models.UserShareFunctionDto

class ManualSplit(user: String) : Split(user) {

    override fun calculateAmount(transaction: Transaction, userShare: List<UserShareFunctionDto>) {
        val shareAmount = userShare.find { it.userName == this.userName }!!.shareAmount!!

        this.amount = if (transaction.paidBy != this.userName)
            shareAmount.unaryMinus()
        else
            shareAmount
    }
}