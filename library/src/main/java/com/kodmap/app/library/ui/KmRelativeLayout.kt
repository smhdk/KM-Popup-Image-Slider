package com.kodmap.app.library.ui

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.kodmap.app.library.R
import android.view.ViewGroup


class KmRelativeLayout : RelativeLayout {

    private var loadingView: View? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.KmRelativeLayout, 0, 0)

        val loadingViewResourceId = ta.getResourceId(R.styleable.KmRelativeLayout_addLoadingView, -1)
        if (loadingViewResourceId != -1) {
            loadingView = findViewById(loadingViewResourceId)
        }

        ta.recycle()
    }

    fun addLoadingLayout(view: View?) {
        if (view == null)
            return
        if (view.getParent() != null)
            (view.getParent() as ViewGroup).removeView(view) // <- fix
        addView(view)
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
        visibility = View.GONE
        loadingView?.visibility = View.VISIBLE
    }
}