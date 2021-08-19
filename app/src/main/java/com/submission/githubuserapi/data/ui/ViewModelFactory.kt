package com.submission.githubuserapi.data.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.submission.githubuserapi.ui.details.DetailsViewModel
import com.submission.githubuserapi.ui.favorite.FavoriteViewModel
import com.submission.githubuserapi.ui.follower.FollowerViewModel
import com.submission.githubuserapi.ui.following.FollowingViewModel
import com.submission.githubuserapi.ui.home.HomeViewModel

class ViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE = ViewModelFactory(application)
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(DetailsViewModel::class.java) -> {
                return DetailsViewModel(mApplication) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                return FavoriteViewModel(mApplication) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                return HomeViewModel() as T
            }
            modelClass.isAssignableFrom(FollowerViewModel::class.java) -> {
                return FollowerViewModel() as T
            }
            modelClass.isAssignableFrom(FollowingViewModel::class.java) -> {
                return FollowingViewModel() as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel : ${modelClass.name}")
        }
    }

}