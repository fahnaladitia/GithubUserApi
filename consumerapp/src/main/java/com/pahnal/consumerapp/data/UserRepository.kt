package com.pahnal.consumerapp.data

import com.pahnal.consumerapp.data.model.User
import com.pahnal.consumerapp.data.source.UserDataSource

class UserRepository(private val source: UserDataSource) {

    fun getUserList(): List<User> = source.getUsers()
}