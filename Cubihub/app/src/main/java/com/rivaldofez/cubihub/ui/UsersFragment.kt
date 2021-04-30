package com.rivaldofez.cubihub.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivaldofez.cubihub.R
import com.rivaldofez.cubihub.adapter.UsersAdapter
import com.rivaldofez.cubihub.databinding.FragmentUsersBinding
import com.rivaldofez.cubihub.listener.OnItemClickListener
import com.rivaldofez.cubihub.model.User
import com.rivaldofez.cubihub.viewmodel.SearchUserViewModel

class UsersFragment : Fragment() {
    private lateinit var userAdapter : UsersAdapter
    private lateinit var binding: FragmentUsersBinding
    private lateinit var searchUserViewModel: SearchUserViewModel
    private var progressState = false
    private var errorState = false

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(binding.toolbarUser)
        appCompatActivity.supportActionBar?.setDisplayShowTitleEnabled(false)

        searchUserViewModel = ViewModelProvider(requireActivity()).get(SearchUserViewModel::class.java)

        userAdapter = UsersAdapter(requireActivity())
        binding.rvUsers.layoutManager = LinearLayoutManager(activity)
        binding.rvUsers.adapter = userAdapter

        userAdapter.setOnClickItemListener(object : OnItemClickListener {
            override fun onItemClick(item: View, userSearch: User) {
                val gotoDetailFragment =
                    UsersFragmentDirections.actionNavigationUsersToUserDetailFragment(userSearch.login)
                findNavController().navigate(gotoDetailFragment)
            }
        })

        searchUserViewModel.listSearchedUser.observe(viewLifecycleOwner, { userList ->
            if(userList.items.isEmpty()){
                binding.imgMessages.visibility = View.VISIBLE
                binding.tvMessages.text = "Data tidak ditemukan, coba kata kunci lain"
                binding.tvMessages.visibility = View.VISIBLE
                binding.rvUsers.visibility = View.GONE
            }else{
                userAdapter.setUsers(userList.items)
                binding.rvUsers.visibility = View.VISIBLE
            }
        })

        searchUserViewModel.showProgress.observe(viewLifecycleOwner, { progressState ->
            this.progressState = progressState
            showProgress(progressState)
        })

        searchUserViewModel.errorState.observe(viewLifecycleOwner, { errorState ->
            this.errorState = errorState
            showMessageError(errorState)
        })

        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        binding.searchView.queryHint = requireActivity().getString(R.string.search_hint)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.tvMessages.visibility = View.GONE
                query?.let { searchUserViewModel.searchUsers(it) }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.settings -> {
                findNavController().navigate(R.id.action_navigation_users_to_settingsFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showProgress(state: Boolean){
        if(state){
            binding.tvMessages.visibility = View.GONE
            binding.imgMessages.visibility = View.GONE
            binding.rvUsers.visibility = View.GONE
            binding.shimmerLoading.visibility = View.VISIBLE
            binding.shimmerLoading.startShimmerAnimation()

        }else{
            binding.shimmerLoading.stopShimmerAnimation()
            binding.shimmerLoading.visibility = View.GONE
        }
    }

    private fun showMessageError(state: Boolean){
        if(state){
            binding.tvMessages.visibility = View.VISIBLE
            binding.imgMessages.visibility = View.VISIBLE
            binding.tvMessages.text = "Terjadi kesalahan, mohon periksa kembali koneksi"
        }else{
            binding.tvMessages.visibility = View.GONE
            binding.imgMessages.visibility = View.GONE
        }
    }
}