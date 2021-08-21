package com.submission.githubuserapi.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.submission.githubuserapi.data.source.local.db.UserDao
import com.submission.githubuserapi.data.source.local.db.UserDatabase
import com.submission.githubuserapi.utils.Constants.AUTHORITY
import com.submission.githubuserapi.utils.Constants.TABLE_NAME

class UserProvider : ContentProvider() {

    private lateinit var mUserDao: UserDao


    override fun onCreate(): Boolean {
        mUserDao = UserDatabase.getInstance(context as Context).userDao()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            USER_LIST -> mUserDao.getAllUserProvider()
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    companion object {

        private const val USER_LIST = 1
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, USER_LIST)
        }
    }

}