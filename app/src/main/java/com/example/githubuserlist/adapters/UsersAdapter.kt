package com.example.githubuserlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserlist.databinding.UsersItemBinding
import com.example.githubuserlist.models.Users
import com.example.githubuserlist.utils.ListDiffUtil

/**
 * [RecyclerView.Adapter] to show a list of users with data binding with [ViewHolder].
 */
class UsersAdapter : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private var usersList = emptyList<Users>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentList = usersList[position]
        holder.bind(currentList)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    fun setData(newList: List<Users>) {
        // Check for changes in the list
        val usersDiffUtil = ListDiffUtil(usersList, newList)
        val diffUtilResult = DiffUtil.calculateDiff(usersDiffUtil)

        usersList = newList

        // Update UI if list has changed
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val binding: UsersItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(users: Users) {
            binding.user = users
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = UsersItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}