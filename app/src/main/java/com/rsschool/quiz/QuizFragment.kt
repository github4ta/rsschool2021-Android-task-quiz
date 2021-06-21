package com.rsschool.quiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    private val questions = arrayListOf<String>(
        "Text of question 1",
        "Text of question 2",
        "Text of question 3",
        "Text of question 4",
        "Text of question 5",
    )

    private val questionOptions = arrayListOf<String>(
        "Question 1 Option 1",
        "Question 1 Option 2",
        "Question 1 Option 3",
        "Question 1 Option 4",
        "Question 1 Option 5",
        "Question 2 Option 1",
        "Question 2 Option 2",
        "Question 2 Option 3",
        "Question 2 Option 4",
        "Question 2 Option 5",
        "Question 3 Option 1",
        "Question 3 Option 2",
        "Question 3 Option 3",
        "Question 3 Option 4",
        "Question 3 Option 5",
        "Question 4 Option 1",
        "Question 4 Option 2",
        "Question 4 Option 3",
        "Question 4 Option 4",
        "Question 4 Option 5",
        "Question 5 Option 1",
        "Question 5 Option 2",
        "Question 5 Option 3",
        "Question 5 Option 4",
        "Question 5 Option 5"
        )
    private var _binding: FragmentQuizBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var communicator: Communicator


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        val view = binding.root
        communicator = activity as Communicator
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val question = arguments?.getString(QUESTION_KEY)
        //val questionOptions = arguments?.getStringArrayList(QUESTION_OPTIONS_KEY)
        val quizStep = arguments?.getInt(QUIZ_STEP_KEY)
        val answers = arguments?.getIntegerArrayList(ANSWERS_KEY)

        binding.toolbar.title = "QUESTION $quizStep"
        if (quizStep != null) {
            val index = quizStep -1
            binding.question.text = questions?.get(index)

            val optionShift = 5 * index
            binding.optionOne.text = questionOptions?.get(0 + optionShift)
            binding.optionTwo.text = questionOptions?.get(1 + optionShift)
            binding.optionThree.text = questionOptions?.get(2 + optionShift)
            binding.optionFour.text = questionOptions?.get(3 + optionShift)
            binding.optionFive.text = questionOptions?.get(4 + optionShift)

            if (answers != null) {
                val checkedId = answers[index]
                if (checkedId != 0) {
                    setCheckedOption(checkedId)
                }
            }

            if (quizStep == 5) {
                binding.nextButton.text = "Submit"
                binding.nextButton.setOnClickListener{
                    if (quizStep != null && answers != null) {
                        answers[quizStep - 1] = getCheckedOption()
                        println("Question $quizStep, answers $answers")
                        communicator.submitButton(answers)
                    }
                }
            } else {
                binding.nextButton.setOnClickListener{
                    if (quizStep != null && answers != null) {
                        answers[quizStep - 1] = getCheckedOption()
                        println("Question $quizStep, answers $answers")
                        communicator.nextButton(quizStep, answers)
                    }
                }
            }

            if (quizStep == 1) {
                binding.previousButton.isClickable = false
                binding.previousButton.isEnabled = false
            } else {
                binding.previousButton.setOnClickListener{
                    if (quizStep != null && answers != null) {
                        answers[quizStep - 1] = getCheckedOption()
                        println("Question $quizStep, answers $answers")
                        communicator.previousButton(quizStep, answers)
                    }
                }
            }
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(
            quizStep: Int,
            question: String,
            questionOptions: ArrayList<String>,
            answers: ArrayList<Int>
        ): QuizFragment {
            val fragment = QuizFragment()
            val args = Bundle()
            args.putInt(QUIZ_STEP_KEY, quizStep)
            args.putIntegerArrayList(ANSWERS_KEY, answers)
            args.putString(QUESTION_KEY, question)
            args.putStringArrayList(QUESTION_OPTIONS_KEY, questionOptions)
            fragment.arguments = args
            return fragment
        }


        private const val ANSWERS_KEY = "ANSWERS"
        private const val QUIZ_STEP_KEY = "QUIZ_STEP"
        private const val QUESTION_KEY = "QUESTION"
        private const val QUESTION_OPTIONS_KEY = "QUESTION_OPTIONS"
    }
}