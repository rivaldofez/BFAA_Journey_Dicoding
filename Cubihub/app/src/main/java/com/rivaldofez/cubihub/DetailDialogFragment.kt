package com.rivaldofez.cubihub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.rivaldofez.cubihub.databinding.FragmentDetailDialogBinding
import com.rivaldofez.cubihub.model.User

class DetailDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentDetailDialogBinding
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var margs = arguments
        user = margs!!.getParcelable<User>(FollowFragment.KEY_DETAIL_USER) as User
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailDialogBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvUsername.text = user.login
        Glide.with(requireContext()).load(user.avatar_url).into(binding.imgContent)

        binding.btnBack.setOnClickListener {
            dismiss()
        }
    }
}