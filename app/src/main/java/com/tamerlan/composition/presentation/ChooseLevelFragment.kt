package com.tamerlan.composition.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.tamerlan.composition.R
import com.tamerlan.composition.databinding.FragmentChooseLevelBinding
import com.tamerlan.composition.domain.entity.Level
import com.tamerlan.composition.presentation.GameFragment.Companion.KEY_LEVEL
import java.lang.RuntimeException

class ChooseLevelFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chooseLevel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun chooseLevel() {
        with(binding) {
            buttonLevelTest.setOnClickListener { launchGameFragment(Level.TEST) }
            buttonLevelEasy.setOnClickListener { launchGameFragment(Level.EASY) }
            buttonLevelNormal.setOnClickListener { launchGameFragment(Level.NORMAL) }
            buttonLevelHard.setOnClickListener { launchGameFragment(Level.HARD) }
        }
    }

    private fun launchGameFragment(level: Level) {
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(R.id.main_container, GameFragment.newInstance(level))
//            .addToBackStack(GameFragment.NAME)
//            .commit()

        val args = Bundle().apply { putParcelable(KEY_LEVEL, level) }
        findNavController().navigate(
            ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level)
        )
    }

    companion object {
        const val NAME = "ChooseLevelFragment"
        fun newInstance() =
            ChooseLevelFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
