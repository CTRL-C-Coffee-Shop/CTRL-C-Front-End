package com.example.ctrl_c.ui.onBoarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ctrl_c.R
import com.example.ctrl_c.databinding.ActivityOnBoardingBinding
import com.example.ctrl_c.ui.onBoarding.OnBoardingActivity.Companion.MAX_STEP


class OnBoardingAdapter : RecyclerView.Adapter<OnBoardingPager>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingPager {
        return OnBoardingPager(
            ActivityOnBoardingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = MAX_STEP

    override fun onBindViewHolder(holder: OnBoardingPager, position: Int) {
        holder.itemView.run {
            with(holder) {
                if (position == 0) {
                    bindingDesign.tvTitle.text = context.getString(R.string.title_onboarding_1)
                    bindingDesign.ivIllustration.setImageResource(R.drawable.on_boarding1_image)
                } else {
                    bindingDesign.tvTitle.text = context.getString(R.string.title_onboarding_2)
                    bindingDesign.ivIllustration.setImageResource(R.drawable.on_boarding2_image)
                }
            }
        }
    }
}