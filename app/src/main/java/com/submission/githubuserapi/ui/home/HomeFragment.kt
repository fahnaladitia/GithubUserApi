package com.submission.githubuserapi.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.githubuserapi.R
import com.submission.githubuserapi.data.source.remote.model.User
import com.submission.githubuserapi.data.ui.BaseFragment
import com.submission.githubuserapi.data.ui.ListAdapter
import com.submission.githubuserapi.databinding.FragmentHomeBinding
import com.submission.githubuserapi.ui.details.DetailsActivity
import com.submission.githubuserapi.utils.toGone
import com.submission.githubuserapi.utils.toVisible
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment, Fragment(R.layout.fragment_home) {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private val adapter = ListAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(HomeViewModel::class.java)
        setAdapter(adapter)

        viewModel.setSearchQuery("a")
        var job: Job? = null
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String): Boolean {
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                job?.cancel()
                job = MainScope().launch {
                    delay(500L)
                    text?.let {
                        if (text.toString().isNotEmpty()) {
                            binding.progressBar.toVisible()
                            viewModel.setSearchQuery(text.toString().replace(" ", ""))
                        } else {
                            viewModel.setSearchQuery("a")
                        }
                    }
                }
                return false
            }
        })

        viewModel.listUsers.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                adapter.setList(response)
                binding.progressBar.toGone()
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
                    val i = Intent(context, DetailsActivity::class.java).apply {
                        putExtra(DetailsActivity.EXTRA_USERNAME, user.login)
                    }
                    startActivity(i)
                }
            })
        }
    }

}