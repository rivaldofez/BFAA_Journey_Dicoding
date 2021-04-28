package com.rivaldofez.cubihub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rivaldofez.cubihub.R
import com.rivaldofez.cubihub.databinding.ItemUserBinding
import com.rivaldofez.cubihub.listener.OnFavoriteClickListener
import com.rivaldofez.cubihub.listener.OnItemClickListener
import com.rivaldofez.cubihub.model.DetailUser
import com.rivaldofez.cubihub.model.User

class FavoriteAdapter(val context: Context): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private val favoriteUsers : MutableList<DetailUser> = mutableListOf()
    private var onSelectedListener: OnFavoriteClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindModel(favoriteUsers[position])
    }

    override fun getItemCount(): Int {
        return favoriteUsers.size
    }

    fun getSelectedItem(position: Int) : DetailUser{
        return favoriteUsers[position]
    }

    fun deleteSelectedItem(position: Int){
        favoriteUsers.removeAt(position)
        notifyDataSetChanged()
    }

    fun setFavoriteUsers(data: List<DetailUser>){
        favoriteUsers.clear()
        favoriteUsers.addAll(data)
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindModel(detailUser: DetailUser){
            with(binding){
                if(detailUser.login != "null")
                    binding.tvUsername.text = detailUser.login
                else
                    binding.tvUsername.text = context.getString(R.string.nulldata)

                if(detailUser.type != "null")
                    binding.tvType.text = detailUser.type
                else
                    binding.tvType.text = context.getString(R.string.nulldata)

                if(detailUser.html_url != "null")
                    binding.tvUrl.text = detailUser.html_url
                else
                    binding.tvUrl.text = context.getString(R.string.nulldata)
                Glide.with(context).load(detailUser.avatar_url).into(binding.imgAvatar)
            }
        }

        init {
            with(binding){
                cvUsers.setOnClickListener{
                    onSelectedListener?.onFavoriteDetail(it, favoriteUsers[layoutPosition])
                }
            }
        }
    }

    fun setOnClickItemListener(onFavoriteClickListener : OnFavoriteClickListener){
        this.onSelectedListener = onFavoriteClickListener
    }
}