package com.rivaldofez.cubihub

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.rivaldofez.cubihub.adapter.DetailPagerAdapter
import com.rivaldofez.cubihub.databinding.FragmentUserDetailBinding
import com.rivaldofez.cubihub.model.DetailUser
import com.rivaldofez.cubihub.viewmodel.DetailUserViewModel
import com.rivaldofez.cubihub.viewmodel.FavoriteUserViewModel


class UserDetailFragment : Fragment() {
    companion object {
        @StringRes
        val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    lateinit var binding: FragmentUserDetailBinding
    private lateinit var username: String
    private lateinit var detailUserViewModel: DetailUserViewModel
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        detailUserViewModel = ViewModelProvider(requireActivity()).get(DetailUserViewModel::class.java)
        favoriteUserViewModel = ViewModelProvider(requireActivity()).get(FavoriteUserViewModel::class.java)

        detailUserViewModel.loadDetailUser(username)
        detailUserViewModel.detailUser.observe(viewLifecycleOwner,{
            setUserView(it)

            favoriteUserViewModel.insertUser(requireContext().applicationContext,it)
            favoriteUserViewModel.getFavoriteUsers(requireContext().applicationContext)
            favoriteUserViewModel.deleteUser(requireContext().applicationContext,"1720517")
            Log.d("Testong", "Delete Activity")
        })

        favoriteUserViewModel.getFavoriteUserById(requireContext().applicationContext, "3304703")

        favoriteUserViewModel.favoriteUser.observe(viewLifecycleOwner, {
            Log.d("Teston", it.toString())
        })

        detailUserViewModel.showProgress.observe(viewLifecycleOwner,{
            if (it)
                binding.progressBar.visibility = View.VISIBLE
            else
                binding.progressBar.visibility = View.GONE
        })

        val detailPagerAdapter = DetailPagerAdapter(requireActivity() as AppCompatActivity)
        detailPagerAdapter.username = username

        binding.viewPager.adapter = detailPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager, ) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun initView() {
        username = UserDetailFragmentArgs.fromBundle(arguments as Bundle).username
    }

    private fun setUserView(detailUser : DetailUser){
        if(detailUser.login != "null") binding.tvUsername.text = detailUser.login
        else binding.tvUsername.text = getString(R.string.nulldata)

        if(detailUser.name != "null") binding.tvFullname.text = detailUser.name
        else binding.tvFullname.text = getString(R.string.nulldata)

        if(detailUser.location != "null") binding.tvLocation.text = detailUser.location
        else binding.tvLocation.text = getString(R.string.nulldata)

        if(detailUser.public_repos != null) binding.tvRepository.text = detailUser.public_repos.toString()
        else  binding.tvRepository.text = getString(R.string.nulldata)

        if( detailUser.followers != null) binding.tvFollower.text = detailUser.followers.toString()
        else  binding.tvFollower.text = getString(R.string.nulldata)

        if(  detailUser.following != null)  binding.tvFollowing.text = detailUser.following.toString()
        else   binding.tvFollowing.text = getString(R.string.nulldata)

        Glide.with(this).load(detailUser.avatar_url).into(binding.imgContent)
    }
}