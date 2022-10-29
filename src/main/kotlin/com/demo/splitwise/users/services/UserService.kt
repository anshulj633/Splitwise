package com.demo.splitwise.users.services

import com.demo.splitwise.users.models.User
import com.demo.splitwise.users.models.UserRequest
import com.demo.splitwise.users.repositories.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun addUser(userRequest: UserRequest): User {
        val user = User(userRequest.userName)
        userRepository.save(user)

        return user
    }

    fun getUserBalance(userName: String): Double {
        return try {
            userRepository.findById(userName).get().balance
        } catch (e: Exception) {
            println(e)
            0.0
        }
    }

    fun getUsers(userNames: List<String>): MutableList<User> {
        try {
            return userRepository.findAllById(userNames) as MutableList<User>
        } catch (e: Exception) {
            println(e)
            return emptyList<User>() as MutableList<User>
        }
    }

    fun getUser(userName: String): User {
        val user = userRepository.findByIdOrNull(userName) ?: User(userName)
        userRepository.save(user)

        return user
    }

    fun updateBalance(userName: String, amount: Double) {
        val user = userRepository.findById(userName).get()
        user.balance = user.balance.plus(amount)
        userRepository.save(user)
    }
}