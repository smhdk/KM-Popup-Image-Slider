package com.kodmap.app.library.ui.zoomableImaveView

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Matrix
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.View
import android.widget.ImageView
import com.kodmap.app.library.R

class KmZoomableImageView : ImageView {
    private var loadingView: View? = null

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs)
    }

    var attacher: PhotoViewAttacher? = null
        private set
    private var pendingScaleType: ImageView.ScaleType? = null


    override fun setScaleType(scaleType: ScaleType?) {
        if (attacher == null) {
            pendingScaleType = scaleType
        } else {
            attacher!!.scaleType = scaleType!!
        }
    }

    override fun getScaleType(): ScaleType {
        return attacher!!.scaleType
    }

    override fun getImageMatrix(): Matrix {
        return attacher!!.imageMatrix
    }

    var isZoomable: Boolean
        get() = attacher!!.isZoomable()
        set(zoomable) {
            attacher!!.setZoomable(zoomable)
        }

    val displayRect: RectF
        get() = attacher!!.displayRect!!

    var minimumScale: Float
        get() = attacher!!.minimumScale
        set(minimumScale) {
            attacher!!.minimumScale = minimumScale
        }

    var mediumScale: Float
        get() = attacher!!.mediumScale
        set(mediumScale) {
            attacher!!.mediumScale = mediumScale
        }

    var maximumScale: Float
        get() = attacher!!.maximumScale
        set(maximumScale) {
            attacher!!.maximumScale = maximumScale
        }

    var scale: Float
        get() = attacher!!.mScale
        set(scale) {
            attacher!!.mScale = scale
        }


    private fun init(attrs: AttributeSet?) {
        attacher = PhotoViewAttacher(this)
        //We always pose as a Matrix scale type, though we can change to another scale type
        //via the attacher
        super.setScaleType(ImageView.ScaleType.MATRIX)
        //apply the previously applied scale type
        if (pendingScaleType != null) {
            scaleType = pendingScaleType!!
            pendingScaleType = null
        }

        if (attrs != null) {
            val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.KmImageView, 0, 0)

            val loadingViewResourceId = ta.getResourceId(R.styleable.KmImageView_setLoadingView, -1)
            if (loadingViewResourceId != -1) {
                loadingView = findViewById(loadingViewResourceId)
            }

            ta.recycle()
        }
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

    override fun setOnLongClickListener(l: View.OnLongClickListener) {
        attacher!!.setOnLongClickListener(l)
    }

    override fun setOnClickListener(l: View.OnClickListener) {
        attacher!!.setOnClickListener(l)
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        // setImageBitmap calls through to this method
        if (attacher != null) {
            attacher!!.update()
        }
    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        if (attacher != null) {
            attacher!!.update()
        }
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        if (attacher != null) {
            attacher!!.update()
        }
    }

    override fun setFrame(l: Int, t: Int, r: Int, b: Int): Boolean {
        val changed = super.setFrame(l, t, r, b)
        if (changed) {
            attacher?.update()
        }
        return changed
    }

    fun setRotationTo(rotationDegree: Float) {
        attacher!!.setRotationTo(rotationDegree)
    }

    fun setRotationBy(rotationDegree: Float) {
        attacher!!.setRotationBy(rotationDegree)
    }

    fun getDisplayMatrix(matrix: Matrix) {
        attacher!!.getDisplayMatrix(matrix)
    }

    fun setDisplayMatrix(finalRectangle: Matrix): Boolean {
        return attacher!!.setDisplayMatrix(finalRectangle)
    }

    fun getSuppMatrix(matrix: Matrix) {
        attacher!!.getSuppMatrix(matrix)
    }

    fun setSuppMatrix(matrix: Matrix): Boolean {
        return attacher!!.setDisplayMatrix(matrix)
    }

    fun setAllowParentInterceptOnEdge(allow: Boolean) {
        attacher!!.setAllowParentInterceptOnEdge(allow)
    }

    fun setScaleLevels(minimumScale: Float, mediumScale: Float, maximumScale: Float) {
        attacher!!.setScaleLevels(minimumScale, mediumScale, maximumScale)
    }

    fun setOnMatrixChangeListener(listener: OnMatrixChangedListener) {
        attacher!!.setOnMatrixChangeListener(listener)
    }

    fun setOnPhotoTapListener(listener: OnPhotoTapListener) {
        attacher!!.setOnPhotoTapListener(listener)
    }

    fun setOnOutsidePhotoTapListener(listener: OnOutsidePhotoTapListener) {
        attacher!!.setOnOutsidePhotoTapListener(listener)
    }

    fun setOnViewTapListener(listener: OnViewTapListener) {
        attacher!!.setOnViewTapListener(listener)
    }

    fun setOnViewDragListener(listener: OnViewDragListener) {
        attacher!!.setOnViewDragListener(listener)
    }

    fun setScale(scale: Float, animate: Boolean) {
        attacher!!.setScale(scale, animate)
    }

    fun setScale(scale: Float, focalX: Float, focalY: Float, animate: Boolean) {
        attacher!!.setScale(scale, focalX, focalY, animate)
    }

    fun setZoomTransitionDuration(milliseconds: Int) {
        attacher!!.setZoomTransitionDuration(milliseconds)
    }

    fun setOnDoubleTapListener(onDoubleTapListener: GestureDetector.OnDoubleTapListener) {
        attacher!!.setOnDoubleTapListener(onDoubleTapListener)
    }

    fun setOnScaleChangeListener(onScaleChangedListener: OnScaleChangedListener) {
        attacher!!.setOnScaleChangeListener(onScaleChangedListener)
    }

    fun setOnSingleFlingListener(onSingleFlingListener: OnSingleFlingListener) {
        attacher!!.setOnSingleFlingListener(onSingleFlingListener)
    }
}
