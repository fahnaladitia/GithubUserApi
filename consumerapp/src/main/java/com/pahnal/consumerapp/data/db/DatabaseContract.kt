package com.pahnal.consumerapp.data.db

import android.net.Uri
import android.provider.BaseColumns
import com.pahnal.consumerapp.utils.Constants.AUTHORITY
import com.pahnal.consumerapp.utils.Constants.SCHEME
import com.pahnal.consumerapp.utils.Constants.TABLE_NAME

object DatabaseContract {
    class UserColumns : BaseColumns {
        companion object {
            const val ID = "id"
            const val LOGIN = "username"
            const val AVATAR_URL = "avatar"
            const val NAME = "name"
            const val PUBLIC_REPOS = "repos"
            const val FOLLOWERS = "followers"
            const val FOLLOWING = "following"
            const val LOCATION = "location"
            const val TYPE = "type"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}