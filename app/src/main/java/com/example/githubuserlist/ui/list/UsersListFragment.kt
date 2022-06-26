package com.example.githubuserlist.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserlist.adapters.UsersAdapter
import com.example.githubuserlist.databinding.FragmentUsersListBinding
import com.example.githubuserlist.models.Users
import com.example.githubuserlist.utils.NetworkResult
import com.example.githubuserlist.utils.observeOnce
import com.example.githubuserlist.viewModels.UsersListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A [Fragment] to display a list of all the github users provided by the API.
 * Single source of truth, data is accessed via the network only if the database is empty.
 */
@AndroidEntryPoint
class UsersListFragment : Fragment() {

    private var _binding: FragmentUsersListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UsersListViewModel

    private val mAdapter by lazy { UsersAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initializing View Models
        viewModel = ViewModelProvider(requireActivity())[UsersListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUsersListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = viewModel

        setupRecyclerView()
        verifyDatabase()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmer()
    }

    // Check for users' data on the database
    private fun verifyDatabase() {
        lifecycleScope.launch {
            viewModel.getUsersFromLocal.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].usersList)
                    hideShimmer()
                } else {
                    // Request the API when db is empty
                    requestApiData()
                }
            }
        }
    }

    // Request data remotely
    private fun requestApiData() {
        viewModel.getUsersFromRemote(createQueries())
        viewModel.usersResponse.observe(viewLifecycleOwner) { response ->
            handleApiResponse(response)
        }
    }

    // Handle the different state of the server response
    private fun handleApiResponse(response: NetworkResult<List<Users>>) {
        when (response) {
            is NetworkResult.Success -> {
                hideShimmer()
                response.data?.let { mAdapter.setData(it) }
            }
            is NetworkResult.Error -> {
                loadDataFromCache()
                hideShimmer()
                Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT)
            }
            is NetworkResult.Loading -> {
                showShimmer()
            }
        }
    }

    // Access the users' list via the cache (db)
    private fun loadDataFromCache() {
        lifecycleScope.launch {
            viewModel.getUsersFromLocal.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].usersList)
                }
            }
        }
    }

    // Create the necessarily queries to add to the API request
    private fun createQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries["per_page"] = "100"
        queries["since"] = "1000"
        return queries
    }

    private fun showShimmer() {
        binding.recyclerView.showShimmer()
    }

    private fun hideShimmer() {
        binding.recyclerView.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}