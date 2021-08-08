package com.submission.githubuserapi.data.ui

import androidx.fragment.app.Fragment

abstract class BaseFragment(R: Int) : Fragment(R) {
    protected abstract fun setAdapter(adapter: ListAdapter)
}