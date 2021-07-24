package com.submission.githubuserapi.ui.details

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.tabs.TabLayout.GRAVITY_FILL
import com.google.android.material.tabs.TabLayoutMediator
import com.submission.githubuserapi.R
import com.submission.githubuserapi.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailsViewModel::class.java)
        if (username != null) {
            viewModel.setDataUser(username)
        }
        viewModel.getDataUser().observe(this) {
            if (it != null) {
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = getString(R.string.follower)
                    tvFollowing.text = getString(R.string.following)
                    tvRepo.text = getString(R.string.repository)
                    tvRepoValue.text = "${it.public_repos}"
                    tvFollowersValue.text = "${it.followers}"
                    tvFollowingValue.text = "${it.following}"
                    Glide.with(this@DetailsActivity)
                        .load(it.avatar_url)
                        .centerCrop()
                        .error(R.drawable.ic_error)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                progressBar.isVisible = false
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                progressBar.isVisible = false
                                tvName.isVisible = true
                                tvUsername.isVisible = true
                                tvFollowing.isVisible = true
                                tvFollowers.isVisible = true
                                tvFollowingValue.isVisible = true
                                tvFollowersValue.isVisible = true
                                tvRepoValue.isVisible = true
                                tvRepo.isVisible = true
                                return false
                            }
                        }).into(ivProfile)
                }
            }
        }

        binding.tabs.tabGravity = GRAVITY_FILL
        val adapter = DetailsAdapter(this)
        val title = arrayOf(getString(R.string.follower), getString(R.string.following))
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = title[position]
        }.attach()

        binding.toolbar.title = "$username"
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}