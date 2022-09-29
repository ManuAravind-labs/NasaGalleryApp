package com.android4you.nasa.presentation.grid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.android4you.nasa.R
import com.android4you.nasa.databinding.FragmentGridBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ImageGridFragment : Fragment() {
    private var _binding: FragmentGridBinding? = null
    private val viewModel: ImageGalleryViewModel by activityViewModels()
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGridBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchAllImage()
        observe()
    }

    private fun observe() {
        viewModel.stateflow
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: ImageViewState) {
        when (state) {
            is ImageViewState.Empty -> Unit
            is ImageViewState.Error -> {
                _binding?.progressBar?.visibility = View.GONE
            }
            is ImageViewState.Success -> {
                _binding?.progressBar?.visibility = View.GONE
                val listAdapter = ImageGridAdapter()
                _binding?.imageList?.adapter = listAdapter
                _binding?.imageList?.layoutManager =
                    GridLayoutManager(context, 2)
                listAdapter.submitList(state.list)
            }
            is ImageViewState.Loading -> {
                _binding?.progressBar?.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
