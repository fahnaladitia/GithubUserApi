package com.pahnal.consumerapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.pahnal.consumerapp.data.UserRepository
import com.pahnal.consumerapp.data.model.User
import com.pahnal.consumerapp.data.source.UserDataSource
import kotlinx.coroutines.Dispatchers

class FavoriteViewModel(private val application: Application) : ViewModel() {


    private val repository: UserRepository
        get() {
            val source = UserDataSource(application.contentResolver)
            return UserRepository(source)
        }
    val listUsers: LiveData<List<User>>
        get() = liveData(Dispatchers.IO) {
            emit(repository.getUserList())
        }

}