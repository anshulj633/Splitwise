package com.demo.splitwise.transactions.models


data class UserExpensesDto(
    val paidBy: String,
    val amount: Double,
    val users: List<UserShareResponseDto>,
    val description: String? = null,
    val splitType: String? = null
)

data class UserShareResponseDto(
    val userName: String,
    val shareAmount: Double
)