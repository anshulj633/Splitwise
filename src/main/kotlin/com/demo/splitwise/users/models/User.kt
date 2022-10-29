package com.demo.splitwise.users.models

import java.util.Date
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id


@Entity(name="users")
class User(
    @Id var userName: String,
    var balance: Double = 0.0
) {
}