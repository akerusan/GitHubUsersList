package com.example.githubuserlist.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuserlist.models.Users

/**
* [DiffUtil] class
* Used to calculate updates for a RecyclerView Adapter.
* See ListAdapter and AsyncListDiffer which can simplify the use of DiffUtil on a background thread.
 */
// TODO: Search how to use AsyncListDiffer
class ListDiffUtil(private val oldList: List<Users>, private val newList: List<Users>): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}