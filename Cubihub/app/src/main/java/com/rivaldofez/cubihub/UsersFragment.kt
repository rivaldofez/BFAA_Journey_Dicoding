package com.rivaldofez.cubihub

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivaldofez.cubihub.adapter.UsersAdapter
import com.rivaldofez.cubihub.databinding.FragmentUsersBinding
import com.rivaldofez.cubihub.listener.OnItemClickListener
import com.rivaldofez.cubihub.model.User
import com.rivaldofez.cubihub.viewmodel.SearchUserViewModel

class UsersFragment : Fragment() {
    companion object {
        val KEY_USERNAME = "username"
    }

    val layoutManager = LinearLayoutManager(activity)
    private lateinit var userAdapter : UsersAdapter
    private lateinit var binding: FragmentUsersBinding
    private lateinit var searchUserViewModel: SearchUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUsersBinding.inflate(inflater,container,false)
        setHasOptionsMenu(true);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(binding.toolbarUser)
        appCompatActivity.supportActionBar?.setDisplayShowTitleEnabled(false)

        searchUserViewModel = ViewModelProvider(activity as AppCompatActivity, ViewModelProvider.NewInstanceFactory()).get(SearchUserViewModel::class.java)

        binding.llNotFound.visibility = View.VISIBLE
        binding.rvUsers.visibility = View.GONE
        showLoading(false)

        userAdapter = UsersAdapter(requireActivity())
        binding.rvUsers.layoutManager = layoutManager
        binding.rvUsers.adapter = userAdapter
        action()

        searchUserViewModel.getSearchedUser().observe(viewLifecycleOwner, { userItems ->
            if(searchUserViewModel.errorState){
                binding.rvUsers.visibility = View.GONE
                binding.llNotFound.visibility = View.VISIBLE
                showLoading(false)
            }else{
                if(userItems != null && userItems.size!=0 ){
                    showLoading(true)
                    userAdapter.setUsers(userItems)
                    binding.rvUsers.visibility = View.VISIBLE
                    binding.llNotFound.visibility = View.GONE
                    showLoading(false)

                }else{
                    binding.rvUsers.visibility = View.GONE
                    binding.llNotFound.visibility = View.VISIBLE
                    showLoading(false)
                }
            }
        })

        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        binding.searchView.queryHint = context!!.getString(R.string.search_hint)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.progressBar.visibility = View.VISIBLE
                binding.llNotFound.visibility = View.GONE
                searchUserViewModel.setSearchedUser(query!!, context!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

    }

    private fun action() {
        userAdapter.setOnClickItemListener(object : OnItemClickListener{
            override fun onItemClick(item: View, userSearch: User) {
                val goToDetailActivity = Intent(context, UserDetailActivity::class.java)
                goToDetailActivity.putExtra(KEY_USERNAME, userSearch.login)
                startActivity(goToDetailActivity)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.language -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}