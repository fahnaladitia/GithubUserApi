package com.submission.githubuserapi.data.remote.model

data class DetailsUserResponse(
    val avatar_url: String,
    val followers: Int,
    val public_repos: Int,
    val following: Int,
    val login: String,
    var name: String
)
