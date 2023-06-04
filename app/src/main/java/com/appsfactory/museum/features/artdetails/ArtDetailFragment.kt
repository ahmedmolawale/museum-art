package com.appsfactory.museum.features.artdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.appsfactory.domain.model.ArtDetails
import com.appsfactory.museum.databinding.FragmentArtDetailsBinding
import com.appsfactory.museum.features.artdetails.ArtDetailsViewModel.UiStateModel
import com.appsfactory.museum.features.artdetails.ArtDetailsViewModel.UiStateModel.ArtDetailsSuccess
import com.appsfactory.museum.features.arts.ART_ID_ARG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ArtDetailFragment : Fragment() {

    private var _binding: FragmentArtDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var artDetailsViewModelFactory: ArtDetailsViewModel.ArtDetailsViewModelFactory

    private val artDetailsAdapter: ArtDetailsAdapter by lazy {
        ArtDetailsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentArtDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: ArtDetailsViewModel by viewModels {
            ArtDetailsViewModel.ArtDetailsViewModelFactory.providesFactory(
                assistedFactory = artDetailsViewModelFactory,
                artId = arguments?.getLong(ART_ID_ARG)!!,
            )
        }
        binding.artDetailsRecyclerView.apply {
            adapter = artDetailsAdapter
            layoutManager = LinearLayoutManager(context)
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collect {
                    handleState(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleState(uiStateModel: UiStateModel) {
        when (uiStateModel) {
            is ArtDetailsSuccess -> {
                showArtDetails(uiStateModel.artDetails, uiStateModel.artDetailsItem)
            }

            else -> {
                // no-op
            }
        }
    }

    private fun showArtDetails(artDetails: ArtDetails, items: List<ArtDetailsItem>) {
        with(binding) {
            artPrimaryImageView.load(artDetails.primaryImage)
            titleToolbar.title = artDetails.department
            artDetailsAdapter.submitList(items)
        }
    }
}
