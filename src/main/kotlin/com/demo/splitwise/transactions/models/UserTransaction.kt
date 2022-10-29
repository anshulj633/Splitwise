package com.demo.splitwise.transactions.models

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

data class UserTransactionId(val transactionId: Int? = null, val userName: String? = null): java.io.Serializable

@Entity(name = "userTransactions")
@IdClass(UserTransactionId::class)
class UserTransaction(
    @Id val transactionId: Int,
    @Id val userName: String,
    val amount: Double
) {

}