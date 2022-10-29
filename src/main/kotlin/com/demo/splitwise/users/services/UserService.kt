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
    /**
     * adds new user
     */
    fun addUser(userRequest: UserRequest): User {
        val user = User(userRequest.userName)
        userRepository.save(user)

        return user
    }

    /**
     * returnd user's aggregate balance
     */
    fun getUserBalance(userName: String): Double {
        return try {
            userRepository.findById(userName).get().balance
        } catch (e: Exception) {
            println(e)
            0.0
        }
    }

    /**
     * returns list of users
     */
    fun getUsers(userNames: List<String>): MutableList<User> {
        return try {
            userRepository.findAllById(userNames) as MutableList<User>
        } catch (e: Exception) {
            println(e)
            emptyList<User>() as MutableList<User>
        }
    }

    fun getUser(userName: String): User {
        val user = userRepository.findByIdOrNull(userName) ?: User(userName)
        userRepository.save(user)

        return user
    }

    /**
     * Create bulk users
     */
    fun createUsers(userNames: List<String>): List<User>{
        val users = getUsers(userNames)
        val registeredUserNames = users.map { it.userName }
        val nonRegisteredUsers = userNames.filter { it !in registeredUserNames }

        nonRegisteredUsers.forEach {
            users.add(addUser(UserRequest(it)))
        }

        return users
    }

    fun updateBalance(userName: String, amount: Double) {
        val user = userRepository.findById(userName).get()
        user.balance = user.balance.plus(amount)
        userRepository.save(user)
    }
}