package com.submission.githubuserapi.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.submission.githubuserapi.data.remote.api.RetrofitServer
import com.submission.githubuserapi.data.remote.model.User
import com.submission.githubuserapi.data.remote.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _listUsers: MutableLiveData<List<User>> = MutableLiveData()
    val listUsers: LiveData<List<User>> get() = _listUsers


    fun setSearchQuery(query: String) {
        RetrofitServer.apiInstance
            .getSearchUser(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>,
                ) {
                    if (response.isSuccessful) {

                        _listUsers.value = response.body()?.items
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("$TAG: Failure", t.message.toString())

                }
            })
    }


    companion object {
        val TAG: String = HomeViewModel::class.java.simpleName
    }


}