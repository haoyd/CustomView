package com.example.customview.pages.anim

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.*
import com.example.customview.BaseActivity
import com.example.customview.R
import kotlinx.android.synthetic.main.activity_tween_anim.*

class TweenAnimActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tween_anim)
    }

    fun translate(view: View) {
        val anim = AnimationUtils.loadAnimation(this, R.anim.anim_translate)
        mStar.startAnimation(anim)
    }

    fun scale(view: View) {
        val anim = ScaleAnimation(0f, 2f, 0f, 2f)
        anim.duration = 3000
        mStar.startAnimation(anim)
    }

    fun rotate(view: View) {
        val anim = RotateAnimation(0f, 360f)
        anim.duration = 3000
        mStar.startAnimation(anim)
    }

    fun alpha(view: View) {
        val anim = AlphaAnimation(0f, 1f)
        anim.duration = 3000
        mStar.startAnimation(anim)
    }

    fun group(view: View) {
        val animSet = AnimationSet(this, null)

        val anim1 = ScaleAnimation(0f, 2f, 0f, 2f)
        anim1.duration = 3000

        val anim2 = RotateAnimation(0f, 360f)
        anim2.duration = 3000

        animSet.addAnimation(anim1)
        animSet.addAnimation(anim2)
        
        mStar.startAnimation(animSet)
    }
}