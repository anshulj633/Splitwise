package com.demo.splitwise.users.controllers

import com.demo.splitwise.users.models.UserBalance
import com.demo.splitwise.users.models.UserRequest
import com.demo.splitwise.users.services.UserBalancesService
import com.demo.splitwise.users.services.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/user"])
class UserController(
    private val userService: UserService,
    private val userBalancesService: UserBalancesService
) {

    @PostMapping("")
    fun addUser(@RequestBody user: UserRequest){
        try {
            userService.addUser(user)
            println("user added!!")
        }
        catch (e: Exception){
            println(e)
        }
    }

    @GetMapping("/{userName}/getOverallBalance")
    fun getBalance(@PathVariable userName: String): Double {
        return userService.getUserBalance(userName)
    }

    @GetMapping("/{userName}/getOutstandingBalances")
    fun getOutstandingBalances(@PathVariable userName: String): List<UserBalance> {
        return userBalancesService.getAllOutstandingBalances(userName)
    }
}