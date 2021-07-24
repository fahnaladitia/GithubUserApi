package com.submission.githubuserapi.ui.details

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.submission.githubuserapi.ui.follower.FollowerFragment
import com.submission.githubuserapi.ui.following.FollowingFragment

class DetailsAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                FollowerFragment()
            }
            1 -> {
                FollowingFragment()
            }
            else -> FollowerFragment()
        }
    }
}
