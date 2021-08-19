package com.submission.githubuserapi.data.source.local

import android.app.Application
import com.submission.githubuserapi.data.source.local.db.UserDao
import com.submission.githubuserapi.data.source.local.db.UserDatabase
import com.submission.githubuserapi.data.source.local.model.UserEntity

class UserRepository(application: Application) {
    private val mUserDao: UserDao

    init {
        val db = UserDatabase.getInstance(application)
        mUserDao = db.userDao()
    }

    fun getAllUser() = mUserDao.getAllUser()

    suspend fun checkUser(id: Long) = mUserDao.checkUser(id)

    suspend fun insertFavoriteUser(userEntity: UserEntity) {
        mUserDao.insert(userEntity)
    }

    suspend fun deleteFavoriteUser(id: Long) {
        mUserDao.delete(id)
    }
}