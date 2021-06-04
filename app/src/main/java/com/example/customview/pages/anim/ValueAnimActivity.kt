package com.example.customview.pages.anim

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationSet
import com.blankj.utilcode.util.ConvertUtils
import com.example.customview.BaseActivity
import com.example.customview.R
import kotlinx.android.synthetic.main.activity_value_anim.*

class ValueAnimActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_value_anim)
    }

    fun translate(view: View) {
        val anim = ValueAnimator.ofFloat(0f, 300f, 0f)
        anim.duration = 3000
        anim.addUpdateListener {
            showAnim.translationX = it.animatedValue as Float
            showAnim.requestLayout()
        }
        anim.start()
    }

    fun scale(view: View) {
        val anim = ObjectAnimator.ofFloat(showAnim, "scaleX", 1f, 3f, 1f)
        anim.duration = 3000
        anim.start()
    }

    fun rotate(view: View) {
        val anim = ObjectAnimator.ofFloat(showAnim, "rotation", 0f, 360f)
        anim.duration = 3000
        anim.start()
    }

    fun alpha(view: View) {
        val anim = ObjectAnimator.ofFloat(showAnim, "alpha", 1f, 0f, 1f)
        anim.duration = 3000
        anim.start()
    }

    fun group(view: View) {
        val anim1 = ObjectAnimator.ofFloat(showAnim, "scaleX", 1f, 3f, 1f)
        anim1.duration = 3000

        val anim2 = ObjectAnimator.ofFloat(showAnim, "rotation", 0f, 360f)
        anim2.duration = 3000

        val anim3 = ObjectAnimator.ofFloat(showAnim, "alpha", 1f, 0f, 1f)
        anim3.duration = 3000

        val animSet = AnimatorSet()
        animSet.play(anim1).with(anim3).before(anim2)
        animSet.start()
    }
}