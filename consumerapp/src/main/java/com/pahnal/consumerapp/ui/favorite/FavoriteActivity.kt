package com.pahnal.consumerapp.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pahnal.consumerapp.R
import com.pahnal.consumerapp.data.model.User
import com.pahnal.consumerapp.data.ui.ListAdapter
import com.pahnal.consumerapp.data.ui.ViewModelFactory
import com.pahnal.consumerapp.databinding.ActivityFavoriteBinding
import com.pahnal.consumerapp.ui.detail.DetailFragment

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private val adapter = ListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.title)
        viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory.getInstance(application)
            )[FavoriteViewModel::class.java]
        setAdapter(adapter)

        viewModel.listUsers.observe(this) { users ->
            if (!users.isNullOrEmpty()) {
                binding.progressBar.visibility = View.GONE
                adapter.setList(users)
            } else {
                binding.progressBar.visibility = View.GONE
                binding.empty.visibility = View.VISIBLE

            }

        }


    }

    private fun setAdapter(adapter: ListAdapter) {
        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.adapter = adapter
            adapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback {
                override fun onItemClicked(user: User) {
                    val fragment = DetailFragment.newInstance(user)
                    fragment.show(supportFragmentManager, getString(R.string.exp_username))
                }
            })
        }
    }
}