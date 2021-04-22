package com.rivaldofez.cubihub

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivaldofez.cubihub.adapter.DetailPagerAdapter
import com.rivaldofez.cubihub.adapter.FollowAdapter
import com.rivaldofez.cubihub.databinding.FragmentFollowBinding
import com.rivaldofez.cubihub.listener.OnItemClickListener
import com.rivaldofez.cubihub.model.User
import com.rivaldofez.cubihub.viewmodel.FollowViewModel

class FollowFragment() : Fragment() {
    companion object{
        val KEY_USERNAME = "username"
        val KEY_OPTION = "option"
        val KEY_DETAIL_USER = "detail"
    }

    val layoutManager = LinearLayoutManager(activity)
    var username:String? = null
    var option:String? = null
    private lateinit var followAdapter : FollowAdapter
    private lateinit var binding: FragmentFollowBinding
    private lateinit var followerViewModel: FollowViewModel
    private lateinit var followingViewModel: FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(savedInstanceState!=null){
            username = savedInstanceState.getString(KEY_USERNAME)
            option = savedInstanceState.getString(KEY_OPTION)
        }

        followAdapter = FollowAdapter(requireActivity())
        binding.rvFollowers.layoutManager = layoutManager
        binding.rvFollowers.adapter = followAdapter

        action()

        if(option!! == DetailPagerAdapter.endFollowers){
            followerViewModel = ViewModelProvider(activity as AppCompatActivity, ViewModelProvider.NewInstanceFactory()).get(
                option!!,FollowViewModel::class.java)
            followerViewModel.setFollowUser(username!!,option!!,context!!)

            followerViewModel.getFollowUser().observe(viewLifecycleOwner, {followItems ->
                if(followerViewModel.errorState){
                    showLoading(false)
                }else{
                    if(followItems != null && followItems.size!=0){
                        showLoading(true)
                        followAdapter.setFollows(followItems)
                        showLoading(false)
                    }else{
                        showLoading(false)
                    }
                }
            })
        }else{
            followingViewModel = ViewModelProvider(activity as AppCompatActivity, ViewModelProvider.NewInstanceFactory()).get(
                option!!,FollowViewModel::class.java)
            followingViewModel.setFollowUser(username!!,option!!,context!!)

            followingViewModel.getFollowUser().observe(viewLifecycleOwner, {followItems ->
                if(followingViewModel.errorState){
                    showLoading(false)
                }else{
                    if(followItems != null && followItems.size!=0){
                        showLoading(true)
                        followAdapter.setFollows(followItems)
                        showLoading(false)
                    }else{
                        showLoading(false)
                    }
                }
            })
        }
    }

    private fun action() {
        followAdapter.setOnClickItemListener(object : OnItemClickListener {
            override fun onItemClick(item: View, userSearch: User) {
                val arg = Bundle()
                arg.putParcelable(KEY_DETAIL_USER, userSearch)

                val detailDialogFragment = DetailDialogFragment()
                detailDialogFragment.arguments = arg

                detailDialogFragment.show(activity!!.supportFragmentManager, "Detail Dialog")
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_USERNAME, username)
        outState.putString(KEY_OPTION, option)
    }

}