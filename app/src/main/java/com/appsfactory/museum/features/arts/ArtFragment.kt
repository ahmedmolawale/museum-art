package com.appsfactory.museum.features.arts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.appsfactory.domain.model.ArtId
import com.appsfactory.museum.databinding.FragmentArtsBinding
import com.appsfactory.museum.features.arts.ArtSearchViewModel.UiStateModel
import com.appsfactory.museum.features.arts.ArtSearchViewModel.UiStateModel.ArtCollection
import com.appsfactory.museum.features.arts.ArtSearchViewModel.UiStateModel.EmptyArtCollection
import com.appsfactory.museum.features.arts.ArtSearchViewModel.UiStateModel.EndLoading
import com.appsfactory.museum.features.arts.ArtSearchViewModel.UiStateModel.Error
import com.appsfactory.museum.features.arts.ArtSearchViewModel.UiStateModel.StartLoading
import com.appsfactory.museum.features.hide
import com.appsfactory.museum.features.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArtFragment : Fragment() {

    private var _binding: FragmentArtsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ArtSearchViewModel by viewModels()
    private val artAdapter: ArtsAdapter by lazy {
        ArtsAdapter { artClicked(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentArtsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.artListRecyclerView.apply {
            adapter = artAdapter
            layoutManager = LinearLayoutManager(context)
        }
        binding.search.addTextChangedListener { a ->
            a?.let { viewModel.getArtCollection(it.toString()) }
        }
        observeState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(uiStateModel: UiStateModel) {
        when (uiStateModel) {
            is StartLoading -> {
                with(binding) {
                    errorArtsTextView.hide()
                    noArtsTextView.hide()
                    artListRecyclerView.hide()
                    loadingProgressBar.show()
                }
            }

            is EndLoading -> {
                binding.loadingProgressBar.hide()
            }

            is ArtCollection -> {
                showArts(uiStateModel.artCollection)
            }

            is EmptyArtCollection -> {
                binding.artListRecyclerView.hide()
                binding.noArtsTextView.show()
            }

            is Error -> {
                binding.artListRecyclerView.hide()
                binding.errorArtsTextView.show()
            }

            else -> {
                // no-op
            }
        }
    }

    private fun showArts(arts: List<ArtId>) {
        with(binding) {
            noArtsTextView.hide()
            errorArtsTextView.hide()
            artListRecyclerView.show()
            artAdapter.submitList(arts)
        }
    }

    private fun artClicked(artId: ArtId) {
        Toast.makeText(context, "Clicked ${artId.value}", Toast.LENGTH_SHORT).show()
    }
}
