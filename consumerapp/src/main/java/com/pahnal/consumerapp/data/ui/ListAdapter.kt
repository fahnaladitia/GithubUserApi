package com.pahnal.consumerapp.data.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.pahnal.consumerapp.R
import com.pahnal.consumerapp.data.model.User
import com.pahnal.consumerapp.databinding.ItemUserBinding
import com.pahnal.consumerapp.utils.Constants.hasInternetConnection
import com.pahnal.consumerapp.utils.Constants.toast

class ListAdapter :
    RecyclerView.Adapter<ListAdapter.HomeViewHolder>() {

    private var list: List<User> = arrayListOf()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<User>?) {
        this.list = list ?: listOf()
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(ivUser)
                tvUsername.text = user.username
                root.setOnClickListener {
                    if (hasInternetConnection(itemView.context)) {
                        onItemClickCallback?.onItemClicked(user)
                    } else {
                        toast(itemView.context, "No Internet Connection")
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}