package com.example.ctrl_c.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ctrl_c.R
import com.example.ctrl_c.databinding.FragmentHomeBinding
import com.example.ctrl_c.databinding.ItemHomePagerBinding
import com.example.ctrl_c.ui.home.HomeFragment.Companion.MAX_PAGE
import com.example.ctrl_c.ui.onBoarding.OnBoardingActivity.Companion.MAX_STEP

class HomeAdapter() : RecyclerView.Adapter<HomePager>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePager {
        return HomePager(
            ItemHomePagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = MAX_PAGE

    override fun onBindViewHolder(holder: HomePager, position: Int) {
        holder.itemView.run {
            with(holder) {
                when (position) {
                    0 -> {
                        bindingDesign.imageView.setImageResource(R.drawable.vouchers)
                    }

                    1 -> {
                        bindingDesign.imageView.setImageResource(R.drawable.voucher2)
                    }

                    else -> {
                        bindingDesign.imageView.setImageResource(R.drawable.default_menu)
                    }
                }
            }
        }
    }
}