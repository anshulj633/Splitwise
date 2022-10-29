package com.demo.splitwise.users.repositories

import com.demo.splitwise.users.models.User
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<User, String>