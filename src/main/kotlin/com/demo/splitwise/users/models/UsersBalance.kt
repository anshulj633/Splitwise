package com.demo.splitwise.users.models

import com.demo.splitwise.transactions.models.UserTransactionId
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass


data class UserBalanceId(val paidBy: String? = null, val paidFor: String? = null): java.io.Serializable

@Entity(name = "userBalances")
@IdClass(UserBalanceId::class)
class UserBalance(
    @Id var paidBy: String,
    @Id var paidFor: String,
    var amount: Double
)