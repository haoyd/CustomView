package com.example.customview.pages.anim

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.customview.BaseActivity
import com.example.customview.R
import kotlinx.android.synthetic.main.activity_frame_anim.*

class FrameAnimActivity : BaseActivity() {

    private lateinit var animDrawable: AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame_anim)

        animDrawable = frameImg.background as AnimationDrawable
    }

    fun startAnim(view: View) {
        animDrawable.start()
    }

    fun stopAnim(view: View) {
        animDrawable.stop()
    }
}