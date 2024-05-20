package com.dm.berxley.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.dm.berxley.quizapp.helpers.Constants
import com.dm.berxley.quizapp.models.Question

class QuestionsActivity : AppCompatActivity(), OnClickListener {

    private var mQuestionsList: ArrayList<Question> ? = null
    private var mCurrentPosition: Int = 1;
    private var mSelectedOptionPosition: Int = 0;
    private var mUsername: String? = null
    private var mCorrectAnswers: Int = 0


    private var tvQuestion: TextView? = null
    private var ivFlag: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var tvProgress: TextView? = null
    private var tv_option_one: TextView? = null
    private var tv_option_two: TextView? = null
    private var tv_option_three: TextView? = null
    private var tv_option_four: TextView? = null
    private var btnSubmit: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        mUsername = intent.getStringExtra(Constants.USER_NAME)

        tvQuestion = findViewById(R.id.tvQuestion)
        ivFlag = findViewById(R.id.ivFlag)
        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tvProgress)
        tv_option_one = findViewById(R.id.tv_option_one)
        tv_option_two = findViewById(R.id.tv_option_two)
        tv_option_three = findViewById(R.id.tv_option_three)
        tv_option_four = findViewById(R.id.tv_option_four)
        btnSubmit = findViewById(R.id.btnSubmit)

        mQuestionsList = Constants.getQuestions();
        setQuestion()

    }

    private fun setQuestion() {
        defaultOptionsView()
        val question = mQuestionsList!![mCurrentPosition - 1]

        progressBar?.setProgress(mCurrentPosition)
        ivFlag?.setImageResource(question.image)
        tvProgress?.text = "$mCurrentPosition/${progressBar?.max}"
        tvQuestion?.text = question.question
        tv_option_one?.text = question.optionOne
        tv_option_two?.text = question.optionTwo
        tv_option_three?.text = question.optionThree
        tv_option_four?.text = question.optionFour

        tv_option_one?.setOnClickListener(this)
        tv_option_two?.setOnClickListener(this)
        tv_option_three?.setOnClickListener(this)
        tv_option_four?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)

        if (mCurrentPosition == mQuestionsList!!.size){
            btnSubmit?.text = "FINISH"
        }else{
            btnSubmit?.text = "SUBMIT"
        }
    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>();

        tv_option_one?.let {
            options.add(it)
        }

        tv_option_two?.let {
            options.add(it)
        }
        tv_option_three?.let {
            options.add(it)
        }
        tv_option_four?.let {
            options.add(it)
        }

        for (option in options){
            option.setTextColor(resources.getColor(R.color.optionsTextColor))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNumber: Int){
        defaultOptionsView()

        mSelectedOptionPosition = selectedOptionNumber

        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.setTextColor(Color.parseColor("#FF9013FE"))
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)

    }

    private fun answerQuestion (answer: Int, bgDrawable: Int){
        when(answer){
            1 -> {
                tv_option_one?.background = ContextCompat.getDrawable(this, bgDrawable)
            }
            2 -> {
                tv_option_two?.background = ContextCompat.getDrawable(this, bgDrawable)
            }
            3 -> {
                tv_option_three?.background = ContextCompat.getDrawable(this, bgDrawable)
            }
            4 -> {
                tv_option_four?.background = ContextCompat.getDrawable(this, bgDrawable)
            }
        }
    }

    override fun onClick(view: View?) {

        when(view?.id){
            R.id.tv_option_one -> {
                tv_option_one?.let {
                    selectedOptionView(it, 1)
                }
            }

            R.id.tv_option_two -> {
                tv_option_two?.let {
                    selectedOptionView(it, 2)
                }
            }

            R.id.tv_option_three -> {
                tv_option_three?.let {
                    selectedOptionView(it, 3)
                }
            }

            R.id.tv_option_four -> {
                tv_option_four?.let {
                    selectedOptionView(it, 4)
                }
            }

            R.id.btnSubmit -> {
                if (mSelectedOptionPosition == 0){
                    mCurrentPosition++

                    when{
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            var intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUsername)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                }else{
                    val question = mQuestionsList?.get(mCurrentPosition - 1)
                    if (question!!.correctAnswer != mSelectedOptionPosition){
                        answerQuestion(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }else{
                        mCorrectAnswers++
                    }
                    answerQuestion(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionsList!!.size){
                        btnSubmit?.text = "FINISH"
                    }else{
                        btnSubmit?.text = "GO TO NEXT QUESTION"
                    }
                }

                mSelectedOptionPosition = 0
            }
        }
    }
}