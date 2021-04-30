package com.rivaldofez.cubihub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rivaldofez.consumerapp.R
import com.rivaldofez.consumerapp.databinding.ItemUserBinding
import com.rivaldofez.cubihub.listener.OnItemClickListener
import com.rivaldofez.cubihub.model.User

class FollowAdapter (val context: Context): RecyclerView.Adapter<FollowAdapter.FollowViewHolder>() {
    private lateinit var onSelectedListener: OnItemClickListener
    private val follows : MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowViewHolder(binding)
    }

    fun setFollows(data: MutableList<User>){
        follows.clear()
        follows.addAll(data)
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        holder.bindModel(follows[position])
    }

    override fun getItemCount(): Int {
        return follows.size
    }

    inner class FollowViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindModel(user: User){
            with(binding){
                if(user.login != "null")
                    binding.tvUsername.text = user.login
                else
                    binding.tvUsername.text = context.getString(R.string.nulldata)

                if(user.type != "null")
                    binding.tvType.text = user.type
                else
                    binding.tvType.text = context.getString(R.string.nulldata)

                if(user.html_url != "null")
                    binding.tvUrl.text = user.html_url
                else
                    binding.tvUrl.text = context.getString(R.string.nulldata)
                Glide.with(context).load(user.avatar_url).into(binding.imgAvatar)
            }
        }

        init {
            with(binding){
                cvUsers.setOnClickListener{
                    onSelectedListener.onItemClick(it, follows[layoutPosition])
                }
            }
        }
    }

    fun setOnClickItemListener(onClickItemListener: OnItemClickListener){
        this.onSelectedListener = onClickItemListener
    }
}