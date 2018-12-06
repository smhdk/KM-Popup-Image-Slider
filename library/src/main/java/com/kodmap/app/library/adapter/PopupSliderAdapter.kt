package com.kodmap.app.library.adapter

import android.graphics.Bitmap
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.kodmap.app.library.R
import com.kodmap.app.library.constant.ScaleType
import com.kodmap.app.library.loader.core.DisplayImageOptions
import com.kodmap.app.library.loader.core.ImageLoader
import com.kodmap.app.library.loader.core.assist.FailReason
import com.kodmap.app.library.loader.core.listener.SimpleImageLoadingListener
import com.kodmap.app.library.model.BaseItem
import com.kodmap.app.library.ui.KmRelativeLayout
import com.kodmap.app.library.ui.zoomableImaveView.KmZoomableImageView
import java.util.*


class PopupSliderAdapter : PagerAdapter() {

    private var mImageScaleType: ImageView.ScaleType = ScaleType.FIT_CENTER
    private var mIsZoomable: Boolean = false
    private lateinit var mLoadingView: View
    private val itemList = ArrayList<BaseItem>()
    val options: DisplayImageOptions = DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build()

    fun setItemList(itemList: List<BaseItem>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return itemList.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = View.inflate(container.context, R.layout.km_item_slider, null) as KmRelativeLayout
        val imageView = itemView.findViewById<KmZoomableImageView>(R.id.km_iv_item_slider)

        if (::mLoadingView.isInitialized) {
            itemView.addLoadingLayout(mLoadingView)
            imageView.setLoadingLayout(mLoadingView)
            imageView.enableLoading()
        }

        imageView.scaleType = mImageScaleType
        imageView.isZoomable = mIsZoomable

        ImageLoader.getInstance()
                .displayImage(
                        if (itemList[position].imageUrl == null) "drawable://${itemList[position].drawableId}" else itemList[position].imageUrl,
                        imageView,
                        options,
                        object : SimpleImageLoadingListener() {
                            override fun onLoadingStarted(imageUri: String, view: View) {

                            }

                            override fun onLoadingFailed(imageUri: String, view: View, failReason: FailReason) {
                                imageView.disableLoading()
                            }

                            override fun onLoadingComplete(imageUri: String, view: View, loadedImage: Bitmap) {
                                imageView.disableLoading()
                            }
                        })

        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    fun setScaleType(type: ImageView.ScaleType) {
        mImageScaleType = type
    }

    fun setLoadingView(mLoadingView: View?) {
        if (mLoadingView != null) {
            this.mLoadingView = mLoadingView
        }
    }

    fun setIsZoomable(bool: Boolean) {
        mIsZoomable = bool
    }


}