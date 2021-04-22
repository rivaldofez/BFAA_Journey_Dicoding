package com.rivaldofez.cubihub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rivaldofez.cubihub.R
import com.rivaldofez.cubihub.databinding.ItemUserBinding
import com.rivaldofez.cubihub.listener.OnItemClickListener
import com.rivaldofez.cubihub.model.User

class UsersAdapter(val context: Context): RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {
    private val users : MutableList<User> = mutableListOf()
    private var onSelectedListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindModel(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun setUsers(data: MutableList<User>){
        users.clear()
        users.addAll(data)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
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
                    onSelectedListener?.onItemClick(it, users[layoutPosition])
                }
            }
        }
    }

    fun setOnClickItemListener(onClickItemListener:OnItemClickListener){
        this.onSelectedListener = onClickItemListener
    }
}

