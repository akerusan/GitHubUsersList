package com.example.githubuserlist.ui.single

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import com.example.githubuserlist.R
import com.example.githubuserlist.databinding.ActivitySingleUserBinding
import com.example.githubuserlist.models.SingleUser
import com.example.githubuserlist.utils.NetworkResult
import com.example.githubuserlist.utils.observeOnce
import com.example.githubuserlist.viewModels.SingleUserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class SingleUserActivity : AppCompatActivity() {

    private val args by navArgs<SingleUserActivityArgs>()

    private lateinit var mBinding: ActivitySingleUserBinding

    private lateinit var viewModel: SingleUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_single_user)

        viewModel = ViewModelProvider(this)[SingleUserViewModel::class.java]

        verifyDatabase()
    }

    private fun verifyDatabase() {
        viewModel.getSingleUserFromLocal(args.id).observeOnce(this, Observer { exist ->
            if (exist != null) {
                mBinding.singleUser = exist.user
                mBinding.executePendingBindings()
            } else {
                requestApiData()
            }
        })
    }

    private fun requestApiData() {
        viewModel.getSingleUserFromRemote(args.username)
        viewModel.singleUserResponse.observe(this@SingleUserActivity, Observer {response ->
            handleApiResponse(response)
        })
    }

    private fun handleApiResponse(response: NetworkResult<SingleUser>) {
        when (response) {
            is NetworkResult.Success -> {
                //hideShimmer()
                response.data?.let {
                    mBinding.singleUser = it
                    mBinding.executePendingBindings()
                }
            }
            is NetworkResult.Error -> {
                //loadDataFromCache()
                //hideShimmer()
                //Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT)
            }
            is NetworkResult.Loading -> {
                //showShimmer()
            }
        }
    }
}