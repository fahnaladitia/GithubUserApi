package com.pahnal.consumerapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.pahnal.consumerapp.data.UserRepository
import com.pahnal.consumerapp.data.model.User
import com.pahnal.consumerapp.data.source.UserDataSource
import kotlinx.coroutines.Dispatchers

class FavoriteViewModel(application: Application) : ViewModel() {
    private val repository: UserRepository
    val listUsers: LiveData<List<User>>

    init {
        val source = UserDataSource(application.contentResolver)
        repository = UserRepository(source)
        listUsers = liveData(Dispatchers.IO) {
            emit(repository.getUserList())
        }
    }

}