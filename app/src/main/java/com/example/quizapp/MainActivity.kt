package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.Adapter.QuizListAdapter
import com.example.quizapp.Model.QuestionModel
import com.example.quizapp.Model.QuizModel
import com.example.quizapp.databinding.ActivityMainBinding
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var quizModeList: MutableList<QuizModel>
    lateinit var adapter: QuizListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizModeList = mutableListOf()
        getData()
    }

    private fun getData() {
//        val listQuestionModel = mutableListOf<QuestionModel>()
//        listQuestionModel.add(
//            QuestionModel(
//                "what is android",
//                mutableListOf("language", "OS", "Product", "None"),
//                "OS"
//            )
//        )
//        listQuestionModel.add(
//            QuestionModel(
//                "what is",
//                mutableListOf("language", "OS", "Product", "None"),
//                "None"
//            )
//        )
//        listQuestionModel.add(
//            QuestionModel(
//                "what android",
//                mutableListOf("language", "OS", "Product", "None"),
//                "Product"
//            )
//        )
//        quizModeList.add(QuizModel("1", "A", "B", "10", listQuestionModel))
////        quizModeList.add(QuizModel("2", "C", "D", "20"))
////        quizModeList.add(QuizModel("3", "E", "F", "30"))
//        setupRecyclerView()


        FirebaseDatabase.getInstance().reference
            .get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val quizModel = snapshot.getValue(QuizModel::class.java)
                        if (quizModel != null) {
                            quizModeList.add(quizModel)
                        }
                    }
                }
                setupRecyclerView()
            }
    }

    private fun setupRecyclerView() {
        adapter = QuizListAdapter(quizModeList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}