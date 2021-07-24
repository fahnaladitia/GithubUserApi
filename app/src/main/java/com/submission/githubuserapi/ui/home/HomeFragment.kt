package com.submission.githubuserapi.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.githubuserapi.R
import com.submission.githubuserapi.data.remote.model.User
import com.submission.githubuserapi.databinding.FragmentHomeBinding
import com.submission.githubuserapi.ui.ListAdapter
import com.submission.githubuserapi.ui.details.DetailsActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        val adapter = ListAdapter(requireContext())
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(HomeViewModel::class.java)
        setAdapter(adapter)


        viewModel.getSearchUsers().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
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

            var job: Job? = null
            searchView.addTextChangedListener { editable ->
                job?.cancel()
                job = MainScope().launch {
                    delay(500L)
                    editable?.let {
                        searchUser()
                    }
                }
            }
        }
        adapter.notifyDataSetChanged()
    }

    private fun searchUser() {
        binding.apply {

            var query: String = searchView.text.toString().replace(" ", "")
            if (query.isEmpty()) {
                query = "a"
            }
            showLoading(true)
            viewModel.setSearchQuery(query)

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