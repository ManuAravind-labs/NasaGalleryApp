package com.android4you.nasa.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android4you.nasa.databinding.FragmentDetailsBinding
import com.android4you.nasa.presentation.grid.ImageGalleryViewModel
import com.android4you.nasa.presentation.grid.ImageViewState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ImageDetailFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    val binding get() = _binding!!
    private val viewModel: ImageGalleryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.iconBack?.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.stateflow.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleTestStateChange(state) }
            .launchIn(lifecycleScope)
    }

    private fun handleTestStateChange(state: ImageViewState) {
        val index = arguments?.getInt("INDEX")
        when (state) {
            is ImageViewState.Empty -> Unit
            is ImageViewState.Error -> {
            }
            is ImageViewState.Success -> {
                state.list?.let {
                    val listAdapter = ImageViewPagerAdapter(requireActivity(), it)
                    _binding?.viewPager?.adapter = listAdapter
                    index?.let {
                        _binding?.viewPager?.setCurrentItem(index, false)
                    }
                }
            }
            is ImageViewState.Loading -> {
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
