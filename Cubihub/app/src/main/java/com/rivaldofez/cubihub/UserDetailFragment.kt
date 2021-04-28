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

    private lateinit var binding: FragmentUserDetailBinding
    private lateinit var username: String
    private lateinit var detailUserViewModel: DetailUserViewModel
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel
    var isFavoriteUser = false
    private lateinit var favoriteUser: DetailUser

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
            favoriteUserViewModel.checkFavoriteUser(requireContext().applicationContext, it.id)
            setUserView(it)
            favoriteUser = it
        })

        favoriteUserViewModel.isFavoriteUser.observe(viewLifecycleOwner,{
            isFavoriteUser = it
            setIconButton(isFavoriteUser)
        })

        binding.btnFavorite.setOnClickListener(View.OnClickListener {
            if(isFavoriteUser){
                favoriteUserViewModel.deleteUser(requireContext().applicationContext, favoriteUser.id)
                isFavoriteUser=!isFavoriteUser
                setIconButton(isFavoriteUser)
            }else{
                favoriteUserViewModel.insertUser(requireContext().applicationContext,favoriteUser)
                isFavoriteUser=!isFavoriteUser
                setIconButton(isFavoriteUser)
            }
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

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
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

    private fun setIconButton(state: Boolean){
        if(state) {
            binding.btnFavorite.setImageDrawable(
                requireActivity().getDrawable(R.drawable.ic_favorite_filled)
            )
        }else {
            binding.btnFavorite.setImageDrawable(
                requireActivity().getDrawable(R.drawable.ic_favorite_unfilled)
            )
        }
    }


}