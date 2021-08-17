package com.submission.githubuserapi.data.source.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.submission.githubuserapi.data.source.local.model.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM table_user")
    fun getAllUser(): List<UserEntity>

    @Query("SELECT count(*) FROM table_user WHERE table_user.id = :id")
    suspend fun checkUser(id: Long): Int

    @Insert(onConflict = REPLACE)
    suspend fun insert(userEntity: UserEntity)


    @Query("DELETE FROM table_user WHERE table_user.id = :id")
    suspend fun delete(id: Long): Int

}