package com.submission.githubuserapi.ui.follower

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.submission.githubuserapi.data.source.remote.api.RetrofitServer
import com.submission.githubuserapi.data.source.remote.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {

    private var _listUsers: MutableLiveData<List<User>> = MutableLiveData()
    val listUsers: LiveData<List<User>> get() = _listUsers

    fun setFollower(username: String) {
        RetrofitServer.apiInstance
            .getFollowers(username)
            .enqueue(object : Callback<List<User>> {
                override fun onResponse(
                    call: Call<List<User>>,
                    response: Response<List<User>>,
                ) {
                    if (response.isSuccessful) {
                        _listUsers.value = response.body()
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    Log.d("$TAG: Failure", t.message.toString())
                }
            })
    }

    companion object {
        val TAG: String = FollowerViewModel::class.java.simpleName
    }

}