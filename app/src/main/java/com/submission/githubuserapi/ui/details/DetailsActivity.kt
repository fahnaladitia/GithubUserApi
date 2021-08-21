package com.submission.githubuserapi.ui.details

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.tabs.TabLayout.GRAVITY_FILL
import com.google.android.material.tabs.TabLayoutMediator
import com.submission.githubuserapi.R
import com.submission.githubuserapi.data.source.local.model.UserEntity
import com.submission.githubuserapi.data.ui.ViewModelFactory
import com.submission.githubuserapi.databinding.ActivityDetailsBinding
import com.submission.githubuserapi.utils.toGone
import com.submission.githubuserapi.utils.toVisible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetailsBinding
    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = obtainViewModel(this)

        if (username != null) {
            viewModel.setDataUser(username)
        }


        viewModel.dataUser.observe(this) { detailUser ->
            if (detailUser != null) {
                binding.apply {

                    var isChecked = false
                    CoroutineScope(Dispatchers.Main).launch {
                        val count = viewModel.checkUser(detailUser.id)
                        if (count > 0) {
                            favorite.isChecked = true
                            isChecked = true
                        } else {
                            favorite.isChecked = false
                            isChecked = false
                        }
                    }


                    favorite.setOnClickListener {
                        isChecked = !isChecked

                        if (isChecked) {
                            viewModel.insert(
                                userEntity = UserEntity(
                                    id = detailUser.id,
                                    login = detailUser.login,
                                    name = detailUser.name,
                                    avatar_url = detailUser.avatar_url,
                                    publicRepos = detailUser.public_repos,
                                    followers = detailUser.followers,
                                    following = detailUser.following,
                                    location = detailUser.location,
                                    type = detailUser.type
                                )
                            )
                        } else {
                            viewModel.delete(detailUser.id)
                        }
                        favorite.isChecked = isChecked
                    }


                    progressBar.toVisible()
                    tvName.text = detailUser.name
                    tvUsername.text = detailUser.login
                    tvFollowers.text = getString(R.string.follower)
                    tvFollowing.text = getString(R.string.following)
                    tvRepo.text = getString(R.string.repository)
                    tvRepoValue.text =
                        if (detailUser.public_repos >= 1000) "${detailUser.public_repos / 1000}K" else "${detailUser.public_repos}"
                    tvFollowersValue.text =
                        if (detailUser.followers >= 1000) "${detailUser.followers / 1000}K" else "${detailUser.followers}"
                    tvFollowingValue.text =
                        if (detailUser.following >= 1000) "${detailUser.following / 1000}K" else "${detailUser.following}"
                    Glide.with(this@DetailsActivity)
                        .load(detailUser.avatar_url)
                        .centerCrop()
                        .error(R.drawable.ic_error)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean,
                            ): Boolean {
                                progressBar.toGone()
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean,
                            ): Boolean {
                                progressBar.toGone()
                                tvName.toVisible()
                                tvUsername.toVisible()
                                tvFollowing.toVisible()
                                tvFollowers.toVisible()
                                tvFollowingValue.toVisible()
                                tvFollowersValue.toVisible()
                                tvRepoValue.toVisible()
                                tvRepo.toVisible()
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

        TabLayoutMediator(binding.tabs, binding.viewPager)
        { tab, position ->
            tab.text = title[position]
        }.attach()
        binding.toolbar.title = username
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailsViewModel {
        return ViewModelProvider(
            this,
            ViewModelFactory.getInstance(activity.application)
        )[DetailsViewModel::class.java]
    }


    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }
}