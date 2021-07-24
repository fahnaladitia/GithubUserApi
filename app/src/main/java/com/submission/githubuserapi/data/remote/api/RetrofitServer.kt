package com.submission.githubuserapi.data.remote.api

import com.submission.githubuserapi.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitServer {

    companion object {
        private fun connectToServer(): Retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val apiInstance: GithubApi = connectToServer().create(GithubApi::class.java)
    }
}