package com.kodmap.app.library.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.kodmap.app.library.R
import com.kodmap.app.library.constant.ScaleType
import com.kodmap.app.library.model.BaseItem
import com.kodmap.app.library.ui.KmRelativeLayout
import com.kodmap.app.library.ui.KmImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.util.*


class PopupSliderAdapter : PagerAdapter() {

    private var mImageScaleType: ImageView.ScaleType = ScaleType.FIT_CENTER
    lateinit var mLoadingView: View
    private val itemList = ArrayList<BaseItem>()

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
        val itemView = View.inflate(container.context, R.layout.item_slider, null) as KmRelativeLayout
        val imageView = itemView.findViewById<KmImageView>(R.id.iv_item)

        if (::mLoadingView.isInitialized) {
            itemView.addLoadingLayout(mLoadingView)
            imageView.setLoadingLayout(mLoadingView)
            imageView.enableLoading()
        }

        imageView.scaleType = mImageScaleType

        Picasso.get().cancelRequest(imageView)
        if (itemList[position].imageUrl == null) {
            Picasso.get()
                    .load(itemList[position].drawableId!!)
                    .into(imageView, object : Callback {
                        override fun onSuccess() {
                            imageView.disableLoading()
                        }

                        override fun onError(e: java.lang.Exception?) {
                            imageView.disableLoading()
                        }
                    })
        } else {
            Picasso.get()
                    .load(itemList[position].imageUrl)
                    .into(imageView, object : Callback {
                        override fun onSuccess() {
                            imageView.disableLoading()
                        }

                        override fun onError(e: java.lang.Exception?) {
                            imageView.disableLoading()
                        }
                    })
        }
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


}