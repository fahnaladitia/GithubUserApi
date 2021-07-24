package com.submission.githubuserapi.ui.following

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.githubuserapi.R
import com.submission.githubuserapi.data.remote.model.User
import com.submission.githubuserapi.databinding.FragmentFollowBinding
import com.submission.githubuserapi.ui.ListAdapter
import com.submission.githubuserapi.ui.details.DetailsActivity

class FollowingFragment : Fragment(R.layout.fragment_follow) {
    private lateinit var viewModel: FollowingViewModel
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowBinding.bind(view)
        val adapter = ListAdapter(requireContext())
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowingViewModel::class.java)
        val username = activity?.intent?.getStringExtra(DetailsActivity.EXTRA_USERNAME).toString()
        setAdapter(adapter)

        showLoading(true)
        viewModel.setFollowing(username)
        showLoading(false)
        viewModel.getFollowing().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(it)
            }
        }
    }

    private fun setAdapter(adapter: ListAdapter) {

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

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


}
