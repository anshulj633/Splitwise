package com.demo.splitwise.users.models

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class UserRequest(
    @NotNull
    @Size(max = 127)
    val userName: String
)
