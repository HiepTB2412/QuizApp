package com.example.quizapp.Model

data class QuizModel(
    val id: String = "",
    val title: String = "",
    val subtitle: String = "",
    val time: String = "",
    val questionList: List<QuestionModel> = listOf()
){
}

data class QuestionModel(
    val question: String = "",
    val option: List<String> = listOf(),
    val correct: String = ""
){

}
