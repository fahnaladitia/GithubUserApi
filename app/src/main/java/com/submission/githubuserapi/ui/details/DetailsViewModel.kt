package com.submission.githubuserapi.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.submission.githubuserapi.data.remote.api.RetrofitServer
import com.submission.githubuserapi.data.remote.model.DetailsUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel : ViewModel() {

    private var _dataUser: MutableLiveData<DetailsUserResponse> = MutableLiveData()
    val dataUser: LiveData<DetailsUserResponse> get() = _dataUser

    fun setDataUser(username: String) {
        RetrofitServer.apiInstance.getUserDetail(username)
            .enqueue(object : Callback<DetailsUserResponse> {
                override fun onResponse(
                    call: Call<DetailsUserResponse>,
                    response: Response<DetailsUserResponse>,
                ) {
                    if (response.isSuccessful) {
                        _dataUser.value = response.body()
                    }
                }

                override fun onFailure(call: Call<DetailsUserResponse>, t: Throwable) {
                    Log.d("$TAG: Failure", t.message.toString())
                }
            })
    }


    companion object {
        val TAG: String = DetailsViewModel::class.java.simpleName
    }
}