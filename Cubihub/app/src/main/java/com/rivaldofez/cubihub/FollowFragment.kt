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
import com.rivaldofez.cubihub.viewmodel.SearchUserViewModel

class FollowFragment() : Fragment() {
    companion object{
        val KEY_USERNAME = "username"
        val KEY_OPTION = "option"
        val KEY_DETAIL_USER = "detail"
    }

    val layoutManager = LinearLayoutManager(activity)
    var username:String? = null
    var option:String? = null
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





        if(option!! == DetailPagerAdapter.endFollowers){
            val followerAdapter = FollowAdapter(requireActivity())
            binding.rvFollowers.layoutManager = layoutManager
            binding.rvFollowers.adapter = followerAdapter
            action(followerAdapter)

            followerViewModel = ViewModelProvider(requireActivity() as AppCompatActivity,ViewModelProvider.NewInstanceFactory()).get(FollowViewModel::class.java)
            followerViewModel.initializeModel(requireContext())
            username?.let { username -> option?.let { option -> followerViewModel.loadFollowUser(username, option) } }

            followerViewModel.listFollowsUser.observe(viewLifecycleOwner,{
                followerAdapter.setFollows(it)
            })

            followerViewModel.showProgress.observe(viewLifecycleOwner,{
                if (it)
                    binding.progressBar.visibility = View.VISIBLE
                else
                    binding.progressBar.visibility = View.GONE
            })
        }else{
            val followingAdapter = FollowAdapter(requireActivity())
            binding.rvFollowers.layoutManager = layoutManager
            binding.rvFollowers.adapter = followingAdapter

            action(followingAdapter)
            followingViewModel = ViewModelProvider(requireActivity() as AppCompatActivity,ViewModelProvider.NewInstanceFactory()).get(FollowViewModel::class.java)
            followingViewModel.initializeModel(requireContext())

            username?.let { username -> option?.let { option -> followingViewModel.loadFollowUser(username, option) } }

            followingViewModel.listFollowsUser.observe(viewLifecycleOwner,{
                followingAdapter.setFollows(it)
            })

            followingViewModel.showProgress.observe(viewLifecycleOwner,{
                if (it)
                    binding.progressBar.visibility = View.VISIBLE
                else
                    binding.progressBar.visibility = View.GONE
            })
        }
    }

    private fun action(adapter: FollowAdapter) {
        adapter.setOnClickItemListener(object : OnItemClickListener {
            override fun onItemClick(item: View, userSearch: User) {
                val arg = Bundle()
                arg.putParcelable(KEY_DETAIL_USER, userSearch)

                val detailDialogFragment = DetailDialogFragment()
                detailDialogFragment.arguments = arg

                detailDialogFragment.show(requireActivity().supportFragmentManager, "Detail Dialog")
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_USERNAME, username)
        outState.putString(KEY_OPTION, option)
    }

}