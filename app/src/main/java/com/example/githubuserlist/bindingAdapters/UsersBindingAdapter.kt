package com.example.githubuserlist.bindingAdapters

import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.githubuserlist.R
import com.example.githubuserlist.ui.list.UsersListFragmentDirections
import java.lang.Exception

/**
 * [BindingAdapter] for users
 */
class UsersBindingAdapter {

    companion object {

        @JvmStatic
        @BindingAdapter("sendUsernameToClickListener", "sendIdToClickListener", requireAll = true)
        fun onUserClickListener(usersItemLayout: ConstraintLayout, username: String, id: Int) {
            usersItemLayout.setOnClickListener {
                try {
                    val action = UsersListFragmentDirections.actionUsersListFragmentToSingleUserActivity(username, id)
                    usersItemLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onSingleUserClickListener", e.toString())
                }
            }
        }

        @JvmStatic
        @BindingAdapter("loadImageUrl")
        fun loadImageUrl(imageView: ImageView, imageUrl: String?) {
            if (imageUrl != null) {
                imageView.load(imageUrl) {
                    crossfade(600)
                    error(R.drawable.ic_picture_error)
                }
            }
        }
    }
}