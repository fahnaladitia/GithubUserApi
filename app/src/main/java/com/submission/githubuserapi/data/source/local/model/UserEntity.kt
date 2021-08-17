package com.submission.githubuserapi.data.source.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "table_user")
@Parcelize
data class UserEntity(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "username")
    val login: String,
    @ColumnInfo(name = "avatar")
    val avatar_url: String,
) : Parcelable