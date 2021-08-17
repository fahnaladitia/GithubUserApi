package com.submission.githubuserapi.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    val id: Long,
    val login: String,
    val avatar_url: String,
) : Parcelable