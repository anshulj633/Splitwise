package com.demo.splitwise.transactions.models

import com.demo.splitwise.transactions.enums.SplitType
import com.demo.splitwise.users.models.User
import javax.persistence.*

@Entity(name="transactions")
class Transaction(
    val paidBy: String,
    val amount: Double = 0.0,
    val splitType: SplitType
) {

    @Id
    @GeneratedValue
    var id: Int? = null
        private set
}