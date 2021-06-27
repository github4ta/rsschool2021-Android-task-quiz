package com.rsschool.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), Communicator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Start Quiz with the first step and zeroed answers
        openQuizFragment(1, arrayListOf(0, 0, 0, 0, 0))
    }

    private fun openQuizFragment(quizStep: Int, answers: ArrayList<Int>) {
        val quizFragment: Fragment = QuizFragment.newInstance(quizStep, answers)
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
        openQuizFragment(quizStep.plus(1), answers)
    }

    override fun previousButton(quizStep: Int, answers: ArrayList<Int>) {
        openQuizFragment(quizStep.minus(1), answers)
    }

    override fun submitButton(answers: ArrayList<Int>) {
        openResultFragment(answers)
    }

    override fun backButton() {
        // Start Quiz with the first step and zeroed answers
        openQuizFragment(1, arrayListOf(0, 0, 0, 0, 0))
    }
}