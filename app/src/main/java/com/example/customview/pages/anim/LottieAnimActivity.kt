package com.example.customview.pages.anim

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.blankj.utilcode.util.BarUtils
import com.example.customview.BaseActivity
import com.example.customview.R
import com.example.customview.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_lottie_anim.*

class LottieAnimActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottie_anim)
    }

    fun playLottieAnim(view: View) {
        lottieAnimView.playHeadAnim()
    }

    fun alert(view: View) {
        lottieAnimView.showAlert()
    }

    fun showLoading(view: View) {
        lottieAnimView.showLoading()
    }

    fun showSuccess(view: View) {
        lottieAnimView.showSuccess()
    }
}