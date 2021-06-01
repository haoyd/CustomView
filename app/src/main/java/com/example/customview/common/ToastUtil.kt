package com.example.customview.common

import android.widget.Toast

fun showToast(msg: String) {
    Toast.makeText(Env.app, msg, Toast.LENGTH_SHORT).show()
}