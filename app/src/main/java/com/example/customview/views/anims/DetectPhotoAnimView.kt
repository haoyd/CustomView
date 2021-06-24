package com.example.customview.views.anims

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.example.customview.R
import kotlinx.android.synthetic.main.view_detect_photo.view.*

class DetectPhotoAnimView : FrameLayout {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    ) {
        View.inflate(context, R.layout.view_detect_photo, this)
        alertContainer.visibility = GONE
        loadingView.visibility = GONE
        successView.visibility = GONE
    }

    fun playHeadAnim() {
        headPositionView.visibility = VISIBLE
        alertContainer.visibility = GONE
        loadingView.visibility = GONE
        successView.visibility = GONE
        headPositionView.setAnimation("Viewfiner.json")
        headPositionView.playAnimation()
    }

    fun showAlert() {
        alertContainer.visibility = VISIBLE
    }

    fun showLoading() {
        headPositionView.visibility = GONE
        alertContainer.visibility = GONE
        loadingView.visibility = VISIBLE
    }

    fun showSuccess() {
        headPositionView.visibility = GONE
        alertContainer.visibility = GONE
        loadingView.visibility = GONE
        successView.visibility = VISIBLE
    }
}