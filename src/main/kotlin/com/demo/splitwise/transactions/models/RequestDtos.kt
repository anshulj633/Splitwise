package com.demo.splitwise.transactions.models

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero


class TransactionRequest private constructor() {
    @NotNull
    val paidBy: String? = null

    @Positive
    val amount: Double = 0.0

    @NotEmpty
    val users: List<UserShare> = emptyList()

    @NotNull
    val description: String? = null

    @NotNull
    @NotBlank
    val splitType: String? = null
}