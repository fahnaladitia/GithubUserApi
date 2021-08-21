package com.submission.githubuserapi.data.source.local.db

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.submission.githubuserapi.data.source.local.model.UserEntity
import com.submission.githubuserapi.utils.Constants.TABLE_NAME

@Dao
interface UserDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllUser(): List<UserEntity>

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllUserProvider(): Cursor

    @Query("SELECT count(*) FROM $TABLE_NAME WHERE $TABLE_NAME.id = :id")
    suspend fun checkUser(id: Long): Int

    @Query("SELECT * FROM $TABLE_NAME WHERE $TABLE_NAME.username = :username")
    fun getUserById(username: String): Cursor

    @Insert(onConflict = REPLACE)
    suspend fun insert(userEntity: UserEntity)


    @Query("DELETE FROM $TABLE_NAME WHERE $TABLE_NAME.id = :id")
    suspend fun delete(id: Long): Int

}