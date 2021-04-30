package com.rivaldofez.cubihub.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rivaldofez.cubihub.adapter.FavoriteAdapter
import com.rivaldofez.cubihub.databinding.FragmentFavoriteBinding
import com.rivaldofez.cubihub.listener.OnFavoriteClickListener
import com.rivaldofez.cubihub.listener.OnSwipeDeleteCallback
import com.rivaldofez.cubihub.model.DetailUser
import com.rivaldofez.cubihub.viewmodel.FavoriteUserViewModel

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteUserAdapter : FavoriteAdapter
    private lateinit var favoriteUserViewModel: FavoriteUserViewModel
    private var progressState = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteUserViewModel = ViewModelProvider(requireActivity()).get(FavoriteUserViewModel::class.java)

        favoriteUserAdapter = FavoriteAdapter(requireContext())
        binding.rvFavoriteUser.layoutManager = LinearLayoutManager(activity)
        binding.rvFavoriteUser.adapter = favoriteUserAdapter

        favoriteUserViewModel.initializeModel(requireActivity())
        action()

        favoriteUserViewModel.getFavoriteUsers(requireActivity())

        favoriteUserViewModel.listFavoriteUser.observe(viewLifecycleOwner, { listDetailUser ->
            if(listDetailUser.isEmpty()){
                binding.imgMessages.visibility = View.VISIBLE
                binding.tvMessages.text = "Data tidak ditemukan, coba kata kunci lain"
                binding.tvMessages.visibility = View.VISIBLE
                binding.rvFavoriteUser.visibility = View.GONE
            }else{
                binding.imgMessages.visibility = View.GONE
                binding.tvMessages.visibility = View.GONE
                binding.rvFavoriteUser.visibility = View.VISIBLE
                favoriteUserAdapter.setFavoriteUsers(listDetailUser)
            }
        })

        favoriteUserViewModel.showProgress.observe(viewLifecycleOwner, { progressState ->
            this.progressState = progressState
            showProgress(progressState)
        })

        val onItemSwiped = object : OnSwipeDeleteCallback( 0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val selectedItems = favoriteUserAdapter.getSelectedItem(viewHolder.adapterPosition)
                favoriteUserViewModel.deleteUser(requireActivity(), selectedItems.id)
                favoriteUserAdapter.deleteSelectedItem(viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(onItemSwiped)
        itemTouchHelper.attachToRecyclerView(binding.rvFavoriteUser)
    }

    private fun action() {
        favoriteUserAdapter.setOnClickItemListener(object  : OnFavoriteClickListener {
            override fun onFavoriteDetail(item: View, favoriteUser: DetailUser) {
                val gotoDetailFragment = favoriteUser.login?.let {
                    FavoriteFragmentDirections.actionNavigationFavoriteToUserDetailFragment(
                        it
                    )
                }
                gotoDetailFragment?.let { findNavController().navigate(it) }
            }
        })
    }

    fun showProgress(state: Boolean){
        if(state){
            binding.tvMessages.visibility = View.GONE
            binding.imgMessages.visibility = View.GONE
            binding.rvFavoriteUser.visibility = View.GONE
            binding.shimmerLoading.visibility = View.VISIBLE
            binding.shimmerLoading.startShimmerAnimation()

        }else{
            binding.shimmerLoading.stopShimmerAnimation()
            binding.shimmerLoading.visibility = View.GONE
        }
    }
}