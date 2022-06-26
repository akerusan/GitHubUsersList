package com.example.githubuserlist.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.githubuserlist.R

/**
 * A [Fragment] to display a list of the favorite users, previously saved by the app user
 * Data would be stored in a separate database
 */
class FavoriteUsersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_users, container, false)
    }
}