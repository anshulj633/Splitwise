package com.demo.splitwise.transactions.models

import javax.validation.constraints.NotBlank

class UserShare private constructor(){

    @NotBlank
    var userName: String? = null

    var shareAmount: Double? = null

    var sharePercentage: Double? = null

    fun convertToUserShareFunctionDto(): UserShareFunctionDto{
        return UserShareFunctionDto(
            this.userName!!,
            this.shareAmount,
            this.sharePercentage
        )
    }
}

data class UserShareFunctionDto(
    var userName: String,
    var shareAmount: Double? = null,
    var sharePercentage: Double? = null
)