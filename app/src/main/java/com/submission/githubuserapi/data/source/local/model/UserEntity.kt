package com.submission.githubuserapi.data.source.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.submission.githubuserapi.utils.Constants.TABLE_NAME
import kotlinx.parcelize.Parcelize


@Entity(tableName = TABLE_NAME)
@Parcelize
data class UserEntity(
    @PrimaryKey
    var id: Long = 0,
    @ColumnInfo(name = "username")
    var login: String = "",
    @ColumnInfo(name = "avatar")
    var avatar_url: String = "",
    @ColumnInfo(name = "name")
    var name: String? = "",
    @ColumnInfo(name = "repos")
    var publicRepos: Int = 0,
    @ColumnInfo(name = "followers")
    var followers: Int = 0,
    @ColumnInfo(name = "following")
    var following: Int = 0,
    @ColumnInfo(name = "location")
    var location: String? = "",
    @ColumnInfo(name = "type")
    var type: String = ""
) : Parcelable