package com.kodmap.app.library.ui

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent


class KmViewPager : ViewPager {

    private var swipeLocked: Boolean = false

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return !swipeLocked && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return !swipeLocked && super.onInterceptTouchEvent(event)
    }

    override fun canScrollHorizontally(direction: Int): Boolean {
        return !swipeLocked && super.canScrollHorizontally(direction)
    }


    fun setSwipeLocked(bool: Boolean) {
        swipeLocked = bool
    }

}
