package com.dm.berxley.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.dm.berxley.quizapp.helpers.Constants

class ResultActivity : AppCompatActivity() {
    private var username: String ? = null
    private var correctAnswers: Int ? = null
    private var totalQuestions: Int ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvName: TextView = findViewById(R.id.tv_name)
        val tvScore: TextView = findViewById(R.id.tv_score)
        val btnFinish: Button = findViewById(R.id.btn_finish)

        username = intent.getStringExtra(Constants.USER_NAME)
        correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS,0)
        totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS,0)

        tvName.text = username
        tvScore.text = "Your Score is $correctAnswers out of $totalQuestions"

        btnFinish.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}