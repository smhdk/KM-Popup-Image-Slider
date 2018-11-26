package com.kodmap.app.library.adapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.kodmap.app.library.R
import com.kodmap.app.library.listener.AdapterClickListener
import com.kodmap.app.library.model.BaseItem
import com.kodmap.app.library.ui.KmImageView
import com.kodmap.app.library.ui.KmRelativeLayout
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception
import java.util.*

class PopupThumbAdapter(private val listener: AdapterClickListener) : ListAdapter<BaseItem, PopupThumbAdapter.ViewHolder>(itemCallback) {

    private val itemList = ArrayList<BaseItem>()
    lateinit var mLoadingView: View
    var oldSelectedPosition = 0

    fun setList(itemList: List<BaseItem>) {
        this.itemList.clear()
        this.itemList.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_thumb, parent, false) as View
        return ViewHolder(itemView, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (itemList[position].isSelected) {
            holder.gradient_view.visibility = View.VISIBLE
        } else {
            holder.gradient_view.visibility = View.GONE
        }

        Picasso.get().cancelRequest(holder.target)
        if (itemList[position].imageUrl == null) {
            Picasso.get()
                    .load(itemList[position].drawableId!!)
                    .into(holder.target)
        } else {
            Picasso.get()
                    .load(itemList[position].imageUrl)
                    .into(holder.target)
        }
    }


    inner class ViewHolder(itemView: View, listener: AdapterClickListener) : RecyclerView.ViewHolder(itemView) {
        var iv_thumb: KmImageView
        var gradient_view: View
        var target: Target

        init {
            iv_thumb = itemView.findViewById(R.id.iv_thumb)
            gradient_view = itemView.findViewById(R.id.gradient_view)

            target = object : Target {
                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    iv_thumb.disableLoading()
                }

                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    iv_thumb.setImageBitmap(bitmap)
                    iv_thumb.disableLoading()
                }

            }

            if (::mLoadingView.isInitialized) {
                (itemView as KmRelativeLayout).addLoadingLayout(mLoadingView)
                iv_thumb.setLoadingLayout(mLoadingView)
            }

            iv_thumb.setOnClickListener {
                listener.onClick(adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun changeSelectedItem(position: Int) {
        if (position != -1) {
            itemList[oldSelectedPosition].isSelected = false
            itemList[position].isSelected = true
            oldSelectedPosition = position
            notifyDataSetChanged()
        }
    }

    fun setLoadingView(mLoadingView: View?) {
        if (mLoadingView != null) {
            this.mLoadingView = mLoadingView
        }
    }

    companion object {
        var itemCallback: DiffUtil.ItemCallback<BaseItem> = object : DiffUtil.ItemCallback<BaseItem>() {
            override fun areItemsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
                return newItem.imageUrl == oldItem.imageUrl
            }

            override fun areContentsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
                return newItem.isSelected == oldItem.isSelected
            }

            override fun getChangePayload(oldItem: BaseItem, newItem: BaseItem): Any? {
                val bundle = Bundle()
                if (oldItem !== newItem)
                    bundle.putParcelable("newItem", newItem)
                return bundle
            }
        }
    }
}
