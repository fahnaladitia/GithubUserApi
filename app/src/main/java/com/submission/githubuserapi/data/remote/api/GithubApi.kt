package com.submission.githubuserapi.data.remote.api

import com.submission.githubuserapi.data.remote.model.DetailsUserResponse
import com.submission.githubuserapi.data.remote.model.User
import com.submission.githubuserapi.data.remote.model.UserResponse
import com.submission.githubuserapi.utils.Constants.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/users")
    @Headers("Authorization: token $API_KEY")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $API_KEY")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailsUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $API_KEY")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $API_KEY")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>

}