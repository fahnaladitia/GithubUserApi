package com.pahnal.consumerapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val username: String,
    val avatar_url: String,
    val name: String?,
    val publicRepos: Int,
    val followers: Int,
    val following: Int,
    val location: String?,
    val type: String
) : Parcelable