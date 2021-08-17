package com.submission.githubuserapi.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.submission.githubuserapi.data.source.local.UserRepository
import com.submission.githubuserapi.data.source.local.model.UserEntity

class FavoriteViewModel(application: Application) : ViewModel() {

    private val mUserRepository = UserRepository(application)
    private var _listUsers: MutableLiveData<List<UserEntity>> = MutableLiveData()
    val listUsers: LiveData<List<UserEntity>> get() = _listUsers

    init {
        _listUsers.value = mUserRepository.getAllUser()

    }
}