package com.example.quizapp.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.Model.QuizModel
import com.example.quizapp.QuizActivity
import com.example.quizapp.databinding.QuizItemRowBinding

class QuizListAdapter(private val quizModeList: List<QuizModel>) :
    RecyclerView.Adapter<QuizListAdapter.MyViewHolder>() {
    inner class MyViewHolder(private val binding: QuizItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: QuizModel) {
            binding.apply {
                txtTitle.text = model.title
                txtSubTitle.text = model.subtitle
                txtTime.text = model.time + " min"
                root.setOnClickListener {
                    val intent = Intent(root.context, QuizActivity::class.java)
                    QuizActivity.questionModelList = model.questionList
                    QuizActivity.time = model.time
                    root.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = QuizItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(quizModeList[position])
    }

    override fun getItemCount(): Int {
        return quizModeList.size
    }
}