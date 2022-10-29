package com.demo.splitwise.strategies

import com.demo.splitwise.transactions.models.Transaction
import com.demo.splitwise.transactions.models.UserShareFunctionDto

abstract class Split(val userName: String) {
    var amount: Double = 0.0

    abstract fun calculateAmount(transaction: Transaction, userShare: List<UserShareFunctionDto>)

}