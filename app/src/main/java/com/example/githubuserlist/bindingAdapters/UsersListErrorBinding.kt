package com.example.githubuserlist.bindingAdapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.githubuserlist.data.database.UsersEntity
import com.example.githubuserlist.models.Users
import com.example.githubuserlist.utils.NetworkResult

/**
 * [BindingAdapter] to handle errors
 */
class UsersListErrorBinding {

    companion object {

        @JvmStatic
        @BindingAdapter("getApiResponse", "getDatabase", requireAll = true)
        fun errorImageView(
            imageView: ImageView,
            response: NetworkResult<List<Users>>?,
            db: List<UsersEntity>?
        ) {
            if (response is NetworkResult.Error && db.isNullOrEmpty()) {
                imageView.visibility = View.VISIBLE
            } else if (response is NetworkResult.Loading) {
                imageView.visibility = View.GONE
            } else if (response is NetworkResult.Success) {
                imageView.visibility = View.GONE
            }
        }

        @JvmStatic
        @BindingAdapter("getApiResponseForText", "getDatabaseForText", requireAll = true)
        fun errorTextView(
            textView: TextView,
            response: NetworkResult<List<Users>>?,
            db: List<UsersEntity>?
        ) {
            if (response is NetworkResult.Error && db.isNullOrEmpty()) {
                textView.visibility = View.VISIBLE
                textView.text = response.message.toString()
            } else if (response is NetworkResult.Loading) {
                textView.visibility = View.GONE
            } else if (response is NetworkResult.Success) {
                textView.visibility = View.GONE
            }
        }
    }
}