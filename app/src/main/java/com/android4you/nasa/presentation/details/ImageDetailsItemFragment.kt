package com.android4you.nasa.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.android4you.nasa.R
import com.android4you.nasa.databinding.FragmentPagerItemBinding
import com.android4you.nasa.domain.model.ImageModelItem

class ImageDetailsItemFragment : Fragment() {
    private var _binding: FragmentPagerItemBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pager_item, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = arguments?.getParcelable<ImageModelItem>("Item")
        _binding?.model = item
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
