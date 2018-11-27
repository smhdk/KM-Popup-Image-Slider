package com.kodmap.app.library.ui

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.kodmap.app.library.R

class KmImageView : ImageView {

    private var loadingView: View? = null
    private var isLoadingFinished = false

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.KmImageView, 0, 0)

        val loadingViewResourceId = ta.getResourceId(R.styleable.KmImageView_setLoadingView, -1)
        if (loadingViewResourceId != -1) {
            loadingView = findViewById(loadingViewResourceId)
        }

        ta.recycle()
    }

    fun setLoadingLayout(view: View?) {
        if (view == null)
            return
        loadingView = view
        loadingView?.visibility = View.VISIBLE
    }

    fun disableLoading() {
        if (loadingView == null)
            return
        loadingView?.visibility = View.GONE
        visibility = View.VISIBLE
    }

    fun enableLoading() {
        if (loadingView == null)
            return
        loadingView?.visibility = View.VISIBLE
        visibility = View.GONE
    }
}