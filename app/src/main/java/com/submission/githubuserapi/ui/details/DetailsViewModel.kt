package com.submission.githubuserapi.ui.details

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.submission.githubuserapi.data.source.local.UserRepository
import com.submission.githubuserapi.data.source.local.model.UserEntity
import com.submission.githubuserapi.data.source.remote.api.RetrofitServer
import com.submission.githubuserapi.data.source.remote.model.DetailsUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel(application: Application) : ViewModel() {

    private var _dataUser: MutableLiveData<DetailsUserResponse> = MutableLiveData()
    val dataUser: LiveData<DetailsUserResponse> get() = _dataUser
    private val mUserRepository = UserRepository(application)

    fun insert(userEntity: UserEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            mUserRepository.insertFavoriteUser(userEntity)
        }
    }

    fun delete(id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            mUserRepository.deleteFavoriteUser(id)
        }
    }

    suspend fun checkUser(id: Long) = mUserRepository.checkUser(id)

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