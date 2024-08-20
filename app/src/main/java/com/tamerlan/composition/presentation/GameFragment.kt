package com.tamerlan.composition.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tamerlan.composition.R
import com.tamerlan.composition.databinding.FragmentGameBinding
import com.tamerlan.composition.databinding.FragmentGameFinishedBinding
import com.tamerlan.composition.domain.entity.GameResult
import com.tamerlan.composition.domain.entity.GameSettings
import com.tamerlan.composition.domain.entity.Level
import java.lang.RuntimeException

class GameFragment : Fragment() {
//    private lateinit var level: Level

    private var percent: Int? = null
    private val args by navArgs<GameFragmentArgs>()

    private val viewModelFactory by lazy {
        GameViewModelFactory(args.level, requireActivity().application)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    private var _binding: FragmentGameBinding? = null

    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setClickListenersToOptions()
    }


    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    private fun setClickListenersToOptions() {
        for (tvOption in tvOptions) {
            tvOption.setOnClickListener() {
                viewModel.chooseAnswer(tvOption.text.toString().toInt())
            }
        }
    }

    private fun observeViewModel() {
        with(viewModel) {
            with(binding) {
                question.observe(viewLifecycleOwner) {
                    tvSum.text = it.sum.toString()
                    tvLeftNumber.text = it.visibleNumber.toString()

                    for (i in 0 until tvOptions.size) {
                        tvOptions[i].text = it.options[i].toString()
                    }
                }

                formattedTime.observe(viewLifecycleOwner) {
                    tvTimer.text = it.toString()
                }

                progressAnswers.observe(viewLifecycleOwner) {
                    tvAnswersProgress.text = it
                }
                viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
                    binding.progressBar.setProgress(it, true)
                    percent = it
                }
                enoughCount.observe(viewLifecycleOwner) {
                    binding.tvAnswersProgress.setTextColor(getColorByState(it))
                }
                enoughPercent.observe(viewLifecycleOwner) {
                    val color = getColorByState(it)
                    progressBar.progressTintList = ColorStateList.valueOf(color)
                }
                minPercent.observe(viewLifecycleOwner) {
                    progressBar.secondaryProgress = it
                }
                gameResult.observe(viewLifecycleOwner) {
                    launchGameFinishedFragment(it, percent ?: 0)
                }


            }
        }
    }


    private fun getColorByState(goodState: Boolean): Int {
        val colorResId = if (goodState) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), colorResId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun parseArgs() {
//        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
//            level = it
//        }
//    }


    private fun launchGameFinishedFragment(gameResult: GameResult, percent: Int) {
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult, percent))
//            .addToBackStack(null)
//            .commit()

//        val args = Bundle().apply {
//            putParcelable(GameFinishedFragment.KEY_GAME_RESULT, gameResult)
//            putInt(GameFinishedFragment.KEY_PERCENT_RIGHT_ANSWERS, percent)
//        }
        findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameFinishedFragment(gameResult,percent))
    }


    companion object {
       const val KEY_LEVEL = "level"

        const val NAME = "GameFragment"

        @JvmStatic
        fun newInstance(level: Level) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
    }
}