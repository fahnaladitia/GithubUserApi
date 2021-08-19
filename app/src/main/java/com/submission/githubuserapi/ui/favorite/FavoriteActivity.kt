package com.submission.githubuserapi.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.githubuserapi.R
import com.submission.githubuserapi.data.source.remote.model.User
import com.submission.githubuserapi.data.ui.ListAdapter
import com.submission.githubuserapi.data.ui.ViewModelFactory
import com.submission.githubuserapi.databinding.ActivityFavoriteBinding
import com.submission.githubuserapi.ui.details.DetailsActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private val adapter = ListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = getString(R.string.favorite)
        setAdapter(adapter)

        viewModel = obtainViewModel(this)
        viewModel.listUsers.observe(this) {
            if (it != null) {
                adapter.setList(it.map { user ->
                    User(
                        id = user.id,
                        login = user.login,
                        avatar_url = user.avatar_url
                    )
                })
            }
        }


        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun setAdapter(adapter: ListAdapter) {
        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.adapter = adapter
            adapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback {
                override fun onItemClicked(user: User) {
                    val i = Intent(this@FavoriteActivity, DetailsActivity::class.java).apply {
                        putExtra(DetailsActivity.EXTRA_USERNAME, user.login)
                    }
                    startActivity(i)
                }
            })
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        return ViewModelProvider(this,
            ViewModelFactory.getInstance(activity.application))[FavoriteViewModel::class.java]
    }
}