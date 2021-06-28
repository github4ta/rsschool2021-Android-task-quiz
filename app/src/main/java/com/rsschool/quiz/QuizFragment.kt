package com.rsschool.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    // Set questions and options from data source
    private val questions = DataProvider().questions
    private val questionOptions = DataProvider().questionOptions

    private var quizStep: Int = 0
    private lateinit var answers: ArrayList<Int>

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val quizStep = arguments?.getInt(QUIZ_STEP_KEY)
        setThemeStyle(quizStep!!, inflater)

        // Use View Binding according to https://developer.android.com/topic/libraries/view-binding
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        val view = binding.root

        communicator = activity as Communicator

        // Handle device back button
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Do nothing when device back button is pressed.
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        quizStep = requireArguments().getInt(QUIZ_STEP_KEY)
        answers = requireArguments().getIntegerArrayList(ANSWERS_KEY) as ArrayList<Int>

        setQuizPage(quizStep, answers)
        setNextButton(quizStep, answers)
        setPreviousButton(quizStep, answers)
        setBackNavigationIcon(quizStep, answers)
    }

    private fun setBackNavigationIcon(quizStep: Int, answers: ArrayList<Int>) {
        if (quizStep == FIRST_QUIZ_STEP) {
            binding.toolbar.navigationIcon = null
        } else {
            binding.toolbar.setNavigationOnClickListener {
                previousButtonOnClickListener(quizStep, answers)
            }
        }
    }

    private fun setPreviousButton(quizStep: Int, answers: ArrayList<Int>) {
        if (quizStep == FIRST_QUIZ_STEP) {
            binding.previousButton.isClickable = false
            binding.previousButton.isEnabled = false
        } else {
            binding.previousButton.setOnClickListener {
                previousButtonOnClickListener(quizStep, answers)
            }
        }
    }

    private fun setNextButton(quizStep: Int, answers: ArrayList<Int>) {
        val isCheckedOption = answers[quizStep - 1] != 0
        binding.nextButton.isClickable = isCheckedOption
        binding.nextButton.isEnabled = isCheckedOption

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            binding.nextButton.isClickable = true
            binding.nextButton.isEnabled = true
            nextSubmitButtonOnClickListener(quizStep, answers)
        }
        nextSubmitButtonOnClickListener(quizStep, answers)
    }

    private fun nextSubmitButtonOnClickListener(quizStep: Int, answers: ArrayList<Int>) {
        if (quizStep == LAST_QUIZ_STEP) {
            // Change the label to Submit for last quiz step
            binding.nextButton.text = "Submit"
            binding.nextButton.setOnClickListener {
                answers[quizStep - 1] = getCheckedOption()
                communicator.submitButton(answers)
            }
        } else {
            binding.nextButton.setOnClickListener {
                answers[quizStep - 1] = getCheckedOption()
                communicator.nextButton(quizStep, answers)
            }
        }
    }

    private fun previousButtonOnClickListener(quizStep: Int, answers: ArrayList<Int>) {
        answers[quizStep - 1] = getCheckedOption()
        communicator.previousButton(quizStep, answers)
    }


    private fun setQuizPage(quizStep: Int, answers: ArrayList<Int>) {
        val index: Int = quizStep.minus(1)
        val optionShift = ANSWER_OPTIONS_NUMBER * index

        binding.toolbar.title = "QUESTION $quizStep"

        binding.question.text = questions[index]

        binding.optionOne.text = questionOptions[0 + optionShift]
        binding.optionTwo.text = questionOptions[1 + optionShift]
        binding.optionThree.text = questionOptions[2 + optionShift]
        binding.optionFour.text = questionOptions[3 + optionShift]
        binding.optionFive.text = questionOptions[4 + optionShift]

        setCheckedOption(answers[quizStep - 1])
    }

    private fun getCheckedOption(): Int {
        var checkedOption = 0
        when {
            binding.optionOne.isChecked -> {
                checkedOption = 1
            }
            binding.optionTwo.isChecked -> {
                checkedOption = 2
            }
            binding.optionThree.isChecked -> {
                checkedOption = 3
            }
            binding.optionFour.isChecked -> {
                checkedOption = 4
            }
            binding.optionFive.isChecked -> {
                checkedOption = 5
            }
        }

        return checkedOption
    }

    private fun setCheckedOption(checkedId: Int): Boolean {
        var isChecked = false
        when (checkedId) {
            1 -> {
                binding.optionOne.toggle()
                isChecked = true
            }
            2 -> {
                binding.optionTwo.toggle()
                isChecked = true
            }
            3 -> {
                binding.optionThree.toggle()
                isChecked = true
            }
            4 -> {
                binding.optionFour.toggle()
                isChecked = true
            }
            5 -> {
                binding.optionFive.toggle()
                isChecked = true
            }
        }

        return isChecked
    }

    private fun setThemeStyle(quizStep: Int, inflater: LayoutInflater) {
        when (quizStep) {
            1 -> {
                inflater.context.setTheme(R.style.Theme1)
            }
            2 -> {
                inflater.context.setTheme(R.style.Theme2)
            }
            3 -> {
                inflater.context.setTheme(R.style.Theme3)
            }
            4 -> {
                inflater.context.setTheme(R.style.Theme4)
            }
            5 -> {
                inflater.context.setTheme(R.style.Theme5)
            }
        }
    }

    // Use View Binding according to https://developer.android.com/topic/libraries/view-binding
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(quizStep: Int, answers: ArrayList<Int>): QuizFragment {
            val fragment = QuizFragment()
            val args = Bundle()
            args.putInt(QUIZ_STEP_KEY, quizStep)
            args.putIntegerArrayList(ANSWERS_KEY, answers)
            fragment.arguments = args
            return fragment
        }

        private const val ANSWERS_KEY = "ANSWERS"
        private const val QUIZ_STEP_KEY = "QUIZ_STEP"
        private const val FIRST_QUIZ_STEP = 1
        private const val LAST_QUIZ_STEP = 5
        private const val ANSWER_OPTIONS_NUMBER = 5
    }
}