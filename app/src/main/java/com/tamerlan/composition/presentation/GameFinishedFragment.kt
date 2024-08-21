package com.tamerlan.composition.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tamerlan.composition.R
import com.tamerlan.composition.databinding.FragmentGameFinishedBinding
import com.tamerlan.composition.domain.entity.GameResult
import java.lang.RuntimeException


    class GameFinishedFragment : Fragment() {

        private val args by navArgs<GameFinishedFragmentArgs>()

        private var _binding: FragmentGameFinishedBinding? = null
        private val binding: FragmentGameFinishedBinding
            get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            setupClickListeners()
            binding.gameResult = args.gameResult
        }

        private fun setupClickListeners() {
            binding.buttonRetry.setOnClickListener {
                retryGame()
            }
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

        private fun retryGame() {
            findNavController().popBackStack()
        }
    }