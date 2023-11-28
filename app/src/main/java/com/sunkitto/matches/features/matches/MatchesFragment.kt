package com.sunkitto.matches.features.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.sunkitto.matches.R
import com.sunkitto.matches.databinding.FragmentMatchesBinding
import com.sunkitto.matches.domain.model.MemoryCard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val NO_IMAGE_RESOURCE = 0

@AndroidEntryPoint
class MatchesFragment : Fragment() {

    private var _binding: FragmentMatchesBinding? = null
    private val binding get() = _binding!!

    private val matchesViewModel: MatchesViewModel by viewModels()

    private val images = mutableListOf(
        R.drawable.ic_favorite_50,
        R.drawable.ic_round_star_50,
        R.drawable.ic_game_pad_50,
        R.drawable.ic_cloudy_50,
        R.drawable.ic_rocket_50,
        R.drawable.ic_wheeler_50,
    ).also { images ->
        images.addAll(images)
        images.shuffle()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchesBinding
            .inflate(
                layoutInflater,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageButtons = listOf(
            binding.imageButton1,
            binding.imageButton2,
            binding.imageButton3,
            binding.imageButton4,
            binding.imageButton5,
            binding.imageButton6,
            binding.imageButton7,
            binding.imageButton8,
            binding.imageButton9,
            binding.imageButton10,
            binding.imageButton11,
            binding.imageButton12,
        )

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                matchesViewModel.mistakesState.collectLatest { mistakes ->
                    val text = getString(R.string.mistakes) + mistakes.toString()
                    binding.mistakesTextView.text = text
                }
            }
        }

        matchesViewModel.memoryCards = imageButtons.indices.map { index ->
            MemoryCard(images[index])
        }

        imageButtons.forEachIndexed { position, imageButton ->
            imageButton.setOnClickListener {
                matchesViewModel.flipCard(position) {
                    val action = MatchesFragmentDirections.actionMatchesFragmentToResultsFragment(
                        mistakes = matchesViewModel.mistakesState.value.toString(),
                    )
                    findNavController().navigate(action)
                }
                matchesViewModel.memoryCards.forEachIndexed { index, memoryCard ->
                    val button = imageButtons[index]
                    if (memoryCard.isMatched) {
                        button.alpha = 0.3f
                    }
                    if(memoryCard.isFlipped) {
                        button.setImageResource(memoryCard.id)
                    } else {
                        button.setImageResource(NO_IMAGE_RESOURCE)
                    }
                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}