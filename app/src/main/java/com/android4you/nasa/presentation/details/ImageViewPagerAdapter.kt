package com.android4you.nasa.presentation.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android4you.nasa.domain.model.ImageModelItem


class ImageViewPagerAdapter(fragmentActivity: FragmentActivity, var list: List<ImageModelItem>) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = ImageDetailsItemFragment()
        val args = Bundle()
        args.putParcelable("Item", list[position])
        fragment.arguments = args
        return fragment
    }
}
