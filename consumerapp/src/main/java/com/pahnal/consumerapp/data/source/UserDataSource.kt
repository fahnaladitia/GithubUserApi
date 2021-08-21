package com.pahnal.consumerapp.data.source

import android.content.ContentResolver
import android.util.Log
import com.pahnal.consumerapp.data.db.DatabaseContract
import com.pahnal.consumerapp.data.db.DatabaseContract.UserColumns.Companion.CONTENT_URI
import com.pahnal.consumerapp.data.model.User

class UserDataSource(private val contentResolver: ContentResolver) {

    fun getUsers(): List<User> {
        val list: MutableList<User> = mutableListOf()
        val cursor = contentResolver.query(
            CONTENT_URI,
            null,
            null,
            null,
            null
        )
        Log.d("TAG", cursor.toString())
        cursor?.apply {
            while (moveToNext()) {
                list.add(
                    User(
                        getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.ID)),
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOGIN)),
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR_URL)),
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.NAME)),
                        getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.PUBLIC_REPOS)),
                        getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWERS)),
                        getInt(getColumnIndexOrThrow(DatabaseContract.UserColumns.FOLLOWING)),
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.LOCATION)),
                        getString(getColumnIndexOrThrow(DatabaseContract.UserColumns.TYPE))
                    )
                )
            }
            close()
        }
        return list.toList()
    }
}