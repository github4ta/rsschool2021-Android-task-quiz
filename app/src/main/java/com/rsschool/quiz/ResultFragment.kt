package com.rsschool.quiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private val expectedAnswers = arrayListOf<String>(
        "Answer 1",
        "Answer 2",
        "Answer 3",
        "Answer 4",
        "Answer 5",
    )

    private var _binding: FragmentResultBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var communicator: Communicator


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root
        communicator = activity as Communicator
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val question = arguments?.getString(QUESTION_KEY)
        //val questionOptions = arguments?.getStringArrayList(QUESTION_OPTIONS_KEY)
        val answers = arguments?.getIntegerArrayList(ANSWERS_KEY)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(answers: ArrayList<Int>
        ): ResultFragment {
            val fragment = ResultFragment()
            val args = Bundle()
            args.putIntegerArrayList(ANSWERS_KEY, answers)
            fragment.arguments = args
            return fragment
        }


        private const val ANSWERS_KEY = "ANSWERS"
    }
}