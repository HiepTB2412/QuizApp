package com.example.quizapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.quizapp.Model.QuestionModel
import com.example.quizapp.databinding.ActivityQuizBinding
import com.example.quizapp.databinding.ScoreDialogBinding

class QuizActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        var questionModelList: List<QuestionModel> = listOf()
        var time: String = ""
    }

    lateinit var binding: ActivityQuizBinding
    var currenQuestionIndex = 0
    var selectAnswer = ""
    var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btnA.setOnClickListener(this@QuizActivity)
            btnB.setOnClickListener(this@QuizActivity)
            btnC.setOnClickListener(this@QuizActivity)
            btnD.setOnClickListener(this@QuizActivity)
            btnNext.setOnClickListener(this@QuizActivity)
        }
        loadQuestion()
        startTimer()
    }

    private fun startTimer() {
        val totalTimeInMillis = time.toInt() * 60 * 1000L
        object : CountDownTimer(totalTimeInMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val remainingSecond = seconds % 60
                binding.timer.text = String.format("%02d:%02d", minutes, remainingSecond)
            }

            override fun onFinish() {
                TODO("Not yet implemented")
            }
        }.start()
    }

    private fun loadQuestion() {
        selectAnswer = ""
        if (currenQuestionIndex == questionModelList.size) {
            finishedQuiz()
            return
        }

        binding.apply {
            tvQuestion.text = "Question ${currenQuestionIndex + 1}/ ${questionModelList.size} "
            progressQuestion.progress =
                (currenQuestionIndex.toFloat() / questionModelList.size.toFloat() * 100).toInt()
            txtContentQuestion.text = questionModelList[currenQuestionIndex].question
            btnA.text = questionModelList[currenQuestionIndex].option[0]
            btnB.text = questionModelList[currenQuestionIndex].option[1]
            btnC.text = questionModelList[currenQuestionIndex].option[2]
            btnD.text = questionModelList[currenQuestionIndex].option[3]
        }
    }

    override fun onClick(view: View?) {
        binding.apply {
            btnA.setBackgroundColor(getColor(R.color.grey))
            btnB.setBackgroundColor(getColor(R.color.grey))
            btnC.setBackgroundColor(getColor(R.color.grey))
            btnD.setBackgroundColor(getColor(R.color.grey))
        }
        val clickedBtn = view as Button
        if (clickedBtn.id == R.id.btnNext) {
            if (selectAnswer == questionModelList[currenQuestionIndex].correct) {
                score++
                Log.i("score of quiz", score.toString())
            }
            currenQuestionIndex++
            loadQuestion()
        } else {
            selectAnswer = clickedBtn.text.toString()
            clickedBtn.setBackgroundColor(getColor(R.color.orange))
        }
    }

    private fun finishedQuiz() {
        val totalQuestion = questionModelList.size
        val percentage = ((score.toFloat() / totalQuestion.toFloat()) * 100).toInt()

        val dialogBinding = ScoreDialogBinding.inflate(layoutInflater)
        dialogBinding.apply {
            scoreProgressIndicator.progress = percentage
            scoreProgressText.text = "${percentage}%"

            if (percentage > 60) {
                scoreTitle.text = "Congrats! You have passed"
                scoreTitle.setTextColor(Color.BLUE)
            } else {
                scoreTitle.text = "Oops! You have failed"
                scoreTitle.setTextColor(Color.RED)
            }
            scoreSubtitle.text = "${score} out of ${totalQuestion} are correct"
            btnFinish.setOnClickListener {
                finish()
            }
        }
        //show dialog
        AlertDialog.Builder(this).setView(dialogBinding.root).setCancelable(false).show()
    }
}