package com.example.customview

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent?.getStringExtra("title")
        if (!TextUtils.isEmpty(title)) {
            setTitle(title)
        }
    }

    protected fun startPage(cls: Class<*>?, pageName: String) {
        val intent = Intent(this, cls)
        intent.putExtra("title", pageName)
        startActivity(intent)
    }

}