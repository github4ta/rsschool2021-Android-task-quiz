package com.rsschool.quiz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentResultBinding
import kotlin.system.exitProcess

class ResultFragment : Fragment() {

    private val expectedAnswers = DataProvider().answers

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var communicator: Communicator


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inflater.context.setTheme(R.style.Theme0)
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root
        communicator = activity as Communicator
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Handle the back button event
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val answers = arguments?.getIntegerArrayList(ANSWERS_KEY) as ArrayList<Int>

        var result = 0
        for (i in 0..4) {
          if (answers[i] == expectedAnswers[i])  {
              result += 20
          }
        }

        binding.txtResult.text = "Your result: $result%"

        binding.btnBack.setOnClickListener {
            communicator.backButton()
        }

        binding.btnExit.setOnClickListener {
            activity?.finish()
            exitProcess(0)
        }

        binding.btnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_SUBJECT, "Quiz results")
                putExtra(Intent.EXTRA_TEXT, getExtraText(result, answers))
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getExtraText(result: Int, answers: ArrayList<Int>): String {
        val extraText = StringBuilder()
        extraText.append("Your result: $result%\n\n")
        for (i in 1..5) {
            val index = 5 * (i - 1) + answers[i - 1] - 1
            extraText.append("$i) ${DataProvider().questions[i - 1]}\n")
            extraText.append("Your answer: ${DataProvider().questionOptions[index]}\n\n")
        }
        return extraText.toString()
    }

    companion object {
        @JvmStatic
        fun newInstance(answers: ArrayList<Int>): ResultFragment {
            val fragment = ResultFragment()
            val args = Bundle()
            args.putIntegerArrayList(ANSWERS_KEY, answers)
            fragment.arguments = args
            return fragment
        }

        private const val ANSWERS_KEY = "ANSWERS"
    }
}