package com.kodmap.app.library

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import com.kodmap.app.library.adapter.PopupSliderAdapter
import com.kodmap.app.library.adapter.PopupThumbAdapter
import com.kodmap.app.library.constant.ListType
import com.kodmap.app.library.constant.ScaleType
import com.kodmap.app.library.constant.ScrollType
import com.kodmap.app.library.listener.AdapterClickListener
import com.kodmap.app.library.model.BaseItem
import com.kodmap.app.library.ui.KmViewPager


class PopopDialogBuilder(private val mContext: Context) {

    private var isThumbPager = true
    private var mImageList = mutableListOf<BaseItem>()
    private var mDialogBgColor: Int = R.color.color_dialog_bg
    private var mHeaderBgColor: Int = R.color.color_dialog_bg
    private var mSliderImageScaleType = ScaleType.FIT_CENTER
    private var mCloseDrawable: Int = R.drawable.ic_close_white_24dp
    private var mLoadingView: View? = null
    private var mDialogStyle: Int = R.style.KmPopupDialog
    private var mSelectorIndicator: Int = R.drawable.indicator_selector

    private lateinit var mAdapterClickListener: AdapterClickListener
    private lateinit var mDialog: Dialog
    private lateinit var mSliderAdapter: PopupSliderAdapter
    private lateinit var mDialogView: View
    private lateinit var mRvThumb: RecyclerView
    private lateinit var mImagePager: KmViewPager
    private lateinit var mThumbAdapter: PopupThumbAdapter

    fun setSelectorIndicator(draw: Int): PopopDialogBuilder {
        mSelectorIndicator = draw
        return this
    }

    fun setDialogStyle(style: Int): PopopDialogBuilder {
        mDialogStyle = style
        return this
    }

    fun setLoadingView(viewId: Int): PopopDialogBuilder {
        mLoadingView = View.inflate(mContext, viewId, null)
        if (mLoadingView == null)
            throw IllegalArgumentException("View could not be inflate")
        else
            return this
    }

    fun setHeaderBackgroundColor(color: Int): PopopDialogBuilder {
        mHeaderBgColor = color
        return this
    }

    fun setCloseDrawable(closeIcon: Int): PopopDialogBuilder {
        mCloseDrawable = closeIcon
        return this
    }

    fun setSliderImageScaleType(type: ImageView.ScaleType): PopopDialogBuilder {
        mSliderImageScaleType = type
        return this
    }

    fun setDialogBackgroundColor(color: Int): PopopDialogBuilder {
        this.mDialogBgColor = color
        return this
    }

    fun setList(imageList: List<Any>?): PopopDialogBuilder {
        this.mImageList.clear()
        if (imageList == null) {
            throw  IllegalArgumentException("List must not be null")
        } else if (imageList.isEmpty()) {
            throw IllegalArgumentException("List must not be empty")
        } else if (imageList[0] is Int) {
            fillImageList(imageList, ListType.Drawable)
            return this
        } else if (imageList[0] is String) {
            fillImageList(imageList, ListType.Url)
            return this
        } else if (imageList[0] is BaseItem) {
            fillImageList(imageList, ListType.BaseItem)
            return this
        } else {
            throw IllegalArgumentException("List contains unsupported type. List contains drawable id (Integer)" +
                    ", image link (String) or BaseItem")
        }
    }

    fun showThumbSlider(isShow: Boolean): PopopDialogBuilder {
        isThumbPager = isShow
        return this
    }

    fun build(): Dialog {
        initListener()
        initDialogView()
        initImageViewPager()
        if (isThumbPager) {
            initThumbReclerView()
        } else {
            initTabLayout()
        }
        initHeader()
        createDialog()
        return mDialog
    }

    private fun initTabLayout() {
        val tabLayout = mDialogView.findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.visibility = View.VISIBLE
        tabLayout.setupWithViewPager(mImagePager)

        if (mSelectorIndicator != R.drawable.indicator_selector) {
            val tabLayoutView = tabLayout.getChildAt(0) as ViewGroup
            for (i in 0 until tabLayout.tabCount) {
                val tab = tabLayoutView.getChildAt(i) as View
                tab.background = ContextCompat.getDrawable(mContext, mSelectorIndicator)
            }
        }
    }

    private fun initHeader() {
        val headerLayout = mDialogView.findViewById<RelativeLayout>(R.id.headerLayout)
        headerLayout.setBackgroundColor(ContextCompat.getColor(mContext, mHeaderBgColor))

        val btn_close = mDialogView.findViewById<ImageView>(R.id.iv_close)
        btn_close.setImageDrawable(ContextCompat.getDrawable(mContext, mCloseDrawable))
        btn_close.setOnClickListener {
            mDialog.dismiss()
        }
    }

    private fun initImageViewPager() {
        mImagePager = mDialogView.findViewById(R.id.viewPager)
        mSliderAdapter = PopupSliderAdapter()
        mSliderAdapter.setLoadingView(mLoadingView)
        mSliderAdapter.setScaleType(mSliderImageScaleType)
        mImagePager.adapter = mSliderAdapter
        (mImagePager.adapter as PopupSliderAdapter).setItemList(mImageList)
        mImagePager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                if (isThumbPager) {
                    setScrollSync(position, ScrollType.PagerScroll)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun initThumbReclerView() {
        mThumbAdapter = PopupThumbAdapter(mAdapterClickListener)
        mRvThumb = mDialogView.findViewById(R.id.rv_thumb)
        mRvThumb.visibility = View.VISIBLE
        mImageList[0].isSelected = true
        mThumbAdapter.setList(mImageList)
        mThumbAdapter.setLoadingView(mLoadingView)
        val layoutManager = LinearLayoutManager(mContext)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mRvThumb.layoutManager = layoutManager
        mRvThumb.adapter = mThumbAdapter
        mRvThumb.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val center = mRvThumb.width / 2
                    val centerView = mRvThumb.findChildViewUnder(center.toFloat(), mRvThumb.left.toFloat())
                    if (centerView != null) {
                        val centerPos = mRvThumb.getChildAdapterPosition(centerView)
                        val firstVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                        if ((firstVisibleItemPosition != 0 || centerPos < (recyclerView.adapter as PopupThumbAdapter).oldSelectedPosition) && (lastVisibleItemPosition != mImageList.size - 1 || centerPos > (recyclerView.adapter as PopupThumbAdapter).oldSelectedPosition)) {
                            setScrollSync(centerPos, ScrollType.RecyclerViewScroll)
                        }
                    }
                    mImagePager.setSwipeLocked(false)
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    mImagePager.setSwipeLocked(true)
                }
            }
        })
    }

    private fun initDialogView() {
        mDialogView = View.inflate(mContext, R.layout.dialog_popup, null)
    }

    private fun initListener() {
        mAdapterClickListener = object : AdapterClickListener {
            override fun onClick(position: Int) {
                mImagePager.currentItem = position
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun createDialog() {
        mDialog = Dialog(mContext, mDialogStyle)
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        val lp = WindowManager.LayoutParams()
        val manager = mContext.getSystemService(Activity.WINDOW_SERVICE) as WindowManager
        val displaymetrics = DisplayMetrics()
        manager.defaultDisplay.getMetrics(displaymetrics);
        lp.width = displaymetrics.widthPixels
        lp.height = displaymetrics.heightPixels

        mDialog.window!!.attributes = lp
        mDialog.window!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(mContext, mDialogBgColor)))

        mDialog.setContentView(mDialogView)
        mDialog.setCancelable(true)
    }

    private fun setScrollSync(position: Int, type: Int) {
        val prevCenterPos = (mRvThumb.adapter as PopupThumbAdapter).oldSelectedPosition
        if (prevCenterPos != position) {
            if (type == ScrollType.RecyclerViewScroll) {
                val center = mRvThumb.width / 2
                val centerView = mRvThumb.findChildViewUnder(center.toFloat(), mRvThumb.left.toFloat())
                mRvThumb.smoothScrollBy(centerView!!.left - center + centerView.width / 2, 0, AccelerateDecelerateInterpolator())
                mImagePager.currentItem = position
            } else if (type == ScrollType.PagerScroll) {
                val view = mRvThumb.layoutManager!!.findViewByPosition(position)
                val middle = mRvThumb.width / 2
                if (view == null) {
                    mRvThumb.scrollToPosition(position)
                } else {
                    mRvThumb.smoothScrollBy(view.left - middle + view.width / 2, 0, AccelerateDecelerateInterpolator())
                }
            }
            (mRvThumb.adapter as PopupThumbAdapter).changeSelectedItem(position)
        }
    }

    private fun fillImageList(imageList: List<Any>, listType: Int) {
        when (listType) {
            ListType.BaseItem -> {
                imageList.forEach {
                    (it as BaseItem).isSelected = false
                    this.mImageList.add(it)
                }
            }
            ListType.Url -> {
                imageList.forEach {
                    val item = BaseItem()
                    item.imageUrl = it as String
                    this.mImageList.add(item)
                }
            }
            ListType.Drawable -> {
                imageList.forEach {
                    val item = BaseItem()
                    item.drawableId = it as Int
                    this.mImageList.add(item)
                }
            }
        }
    }
}
