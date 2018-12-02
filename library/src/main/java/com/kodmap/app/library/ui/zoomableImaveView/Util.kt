package com.kodmap.app.library.ui.zoomableImaveView

import android.os.Build
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import com.kodmap.app.library.apputils.Tls12SocketFactory
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import java.util.ArrayList
import javax.net.ssl.SSLContext

internal object Util {

    fun checkZoomLevels(minZoom: Float, midZoom: Float,
                        maxZoom: Float) {
        if (minZoom >= midZoom) {
            throw IllegalArgumentException(
                    "Minimum zoom has to be less than Medium zoom. Call setMinimumZoom() with a more appropriate value")
        } else if (midZoom >= maxZoom) {
            throw IllegalArgumentException(
                    "Medium zoom has to be less than Maximum zoom. Call setMaximumZoom() with a more appropriate value")
        }
    }

    fun hasDrawable(imageView: ImageView): Boolean {
        return imageView.drawable != null
    }

    fun isSupportedScaleType(scaleType: ImageView.ScaleType?): Boolean {
        if (scaleType == null) {
            return false
        }
        when (scaleType) {
            ImageView.ScaleType.MATRIX -> throw IllegalStateException("Matrix scale type is not supported")
        }
        return true
    }

    fun getPointerIndex(action: Int): Int {
        return action and MotionEvent.ACTION_POINTER_INDEX_MASK shr MotionEvent.ACTION_POINTER_INDEX_SHIFT
    }

    fun enableTls12OnPreLollipop(client: OkHttpClient.Builder): OkHttpClient.Builder {
        if (Build.VERSION.SDK_INT >= 16) {
            try {
                val sc = SSLContext.getInstance("TLSv1.2")
                sc.init(null, null, null)
                client.sslSocketFactory(Tls12SocketFactory(sc.socketFactory))

                val cs = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .build()

                val specs = ArrayList<ConnectionSpec>()
                specs.add(cs)
                specs.add(ConnectionSpec.COMPATIBLE_TLS)
                specs.add(ConnectionSpec.CLEARTEXT)

                client.connectionSpecs(specs)
            } catch (exc: Exception) {
                Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc)
            }

        }

        return client
    }
}
