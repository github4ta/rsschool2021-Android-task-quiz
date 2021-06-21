package com.rsschool.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), Communicator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val quizStep = 1
        val question = "My question"
        val questionOptions = arrayListOf("First option", "Second option", "Third question", "Four question", "Fifth question")
        val answers = arrayListOf(0, 0, 0, 0, 0)
        openQuizFragment(quizStep, question, questionOptions, answers)
    }

    private fun openQuizFragment(quizStep: Int,
                                 question: String,
                                 questionOptions: ArrayList<String>,
                                 answers: ArrayList<Int>) {
        val quizFragment: Fragment = QuizFragment.newInstance(quizStep, question, questionOptions, answers)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, quizFragment)
        transaction.commit()
    }

    private fun openResultFragment(answers: ArrayList<Int>) {
        val resultFragment: Fragment = ResultFragment.newInstance(answers)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, resultFragment)
        transaction.commit()
    }

    override fun nextButton(quizStep: Int, answers: ArrayList<Int>) {
        val question = "My question"
        val questionOptions = arrayListOf("First Option", "Second Option", "Third Option", "Four Option", "Fifth Option")
        openQuizFragment(quizStep + 1, question, questionOptions, answers)
    }

    override fun previousButton(quizStep: Int, answers: ArrayList<Int>) {
        val question = "My question"
        val questionOptions = arrayListOf("First Option", "Second Option", "Third Option", "Four Option", "Fifth Option")
        openQuizFragment(quizStep - 1, question, questionOptions, answers)
    }

    override fun submitButton(answers: ArrayList<Int>) {
        openResultFragment(answers)
    }}