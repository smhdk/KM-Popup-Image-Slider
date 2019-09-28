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
        try {
            return !swipeLocked && super.onTouchEvent(event)
        }catch (e: java.lang.Exception) {
            return !swipeLocked
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        try {
            return !swipeLocked && super.onInterceptTouchEvent(event)
        }catch (e: java.lang.Exception) {
            return !swipeLocked
        }
    }

    override fun canScrollHorizontally(direction: Int): Boolean {
        try {
            return !swipeLocked && super.canScrollHorizontally(direction)
        }catch (e: java.lang.Exception) {
            return !swipeLocked
        }
    }


    fun setSwipeLocked(bool: Boolean) {
        swipeLocked = bool
    }

}
