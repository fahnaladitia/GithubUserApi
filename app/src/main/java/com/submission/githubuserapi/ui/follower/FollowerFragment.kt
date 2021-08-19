package com.submission.githubuserapi.ui.follower

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.githubuserapi.R
import com.submission.githubuserapi.data.source.remote.model.User
import com.submission.githubuserapi.data.ui.BaseFragment
import com.submission.githubuserapi.data.ui.ListAdapter
import com.submission.githubuserapi.data.ui.ViewModelFactory
import com.submission.githubuserapi.databinding.FragmentFollowBinding
import com.submission.githubuserapi.ui.details.DetailsActivity
import com.submission.githubuserapi.utils.toGone
import com.submission.githubuserapi.utils.toVisible

class FollowerFragment : BaseFragment, Fragment(R.layout.fragment_follow) {
    private lateinit var viewModel: FollowerViewModel
    private lateinit var binding: FragmentFollowBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFollowBinding.bind(view)
        val adapter = ListAdapter()
        viewModel = ViewModelProvider(this,
            ViewModelFactory.getInstance(requireActivity().application))[FollowerViewModel::class.java]
        val username = requireActivity().intent.getStringExtra(DetailsActivity.EXTRA_USERNAME)!!
        setAdapter(adapter)

        binding.progressBar.toVisible()
        viewModel.setFollower(username)
        binding.progressBar.toGone()
        viewModel.listUsers.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(it)
            }
        }


    }

    override fun setAdapter(adapter: ListAdapter) {
        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adapter

            adapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback {
                override fun onItemClicked(user: User) {
                    Intent(context, DetailsActivity::class.java).also {
                        it.putExtra(DetailsActivity.EXTRA_USERNAME, user.login)
                        startActivity(it)
                    }
                }
            })
        }
    }
}
