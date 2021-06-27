package com.rsschool.quiz

interface Communicator {
    fun nextButton(quizStep: Int, answers: ArrayList<Int>)

    fun previousButton(quizStep: Int, answers: ArrayList<Int>)

    fun submitButton(answers: ArrayList<Int>)

    fun backButton()
}