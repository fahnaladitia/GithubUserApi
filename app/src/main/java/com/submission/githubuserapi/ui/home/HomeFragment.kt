package com.submission.githubuserapi.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.githubuserapi.R
import com.submission.githubuserapi.data.remote.model.User
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

class HomeFragment : BaseFragment(R.layout.fragment_home) {
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

        viewModel.getSearchUsers().observe(viewLifecycleOwner) { response ->
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
            viewModel.setSearchQuery("a")
            var job: Job? = null
            searchView.addTextChangedListener { editable ->
                job?.cancel()
                job = MainScope().launch {
                    delay(500L)
                    editable?.let {
                        if (editable.toString().isNotEmpty()) {
                            progressBar.toVisible()
                            viewModel.setSearchQuery(editable.toString().replace(" ", ""))
                        } else {
                            viewModel.setSearchQuery("a")
                        }
                    }
                }
            }
        }
    }


}