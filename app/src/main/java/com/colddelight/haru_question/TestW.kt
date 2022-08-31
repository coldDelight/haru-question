package com.colddelight.haru_question

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

//class CustomView @JvmOverloads
//constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
//    : View(context, attrs, defStyleAttr) {
//
//    lateinit var layout : ConstraintLayout
//    lateinit var question : TextView
//    lateinit var date : TextView
//
//    init {
//        val view = LayoutInflater.from(context).inflate(R.layout.test,this,false)
//
//
//    }
//
//
//
//}

class TestW constructor(context: Context,attrs:AttributeSet?):ConstraintLayout(context,attrs){
    private lateinit var layout : ConstraintLayout
    lateinit var tvQ : TextView
    lateinit var tvD : TextView


    init {
        val view = LayoutInflater.from(context).inflate(R.layout.test,this,false)
        addView(view)
        layout = findViewById(R.id.testLayout)
        tvQ = findViewById(R.id.testTvOne)
        tvD = findViewById(R.id.testTvTwo)

    }

    private fun getAttrs(attrs:AttributeSet?){
        //아까 만들어뒀던 속성 attrs 를 참조함
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.Question)
        setTypeArray(typedArray)
    }

    private fun getAttrs(attrs:AttributeSet?, defStyle:Int){
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.Question,defStyle,0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray : TypedArray){
        //텍스트 내용, LoginButton 이름으로 만든 attrs.xml 속성중 text 를 참조함
        val text12 = typedArray.getText(R.styleable.Question_date)
        val text22 = typedArray.getText(R.styleable.Question_text)
        tvQ.text = text12
        tvD.text = text22

        typedArray.recycle()
    }

}