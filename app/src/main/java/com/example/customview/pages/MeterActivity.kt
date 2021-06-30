package com.example.customview.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.customview.BaseActivity
import com.example.customview.R
import kotlinx.android.synthetic.main.activity_meter.*

class MeterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meter)
        circleProgressView.setCurrentProgress(82f)
    }
}