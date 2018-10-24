package com.kodmap.app.kmpopupslider

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.kodmap.app.library.PopopDialogBuilder
import com.kodmap.app.library.constant.ScaleType
import com.kodmap.app.library.model.BaseItem

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawableList = arrayListOf<Int>()
        drawableList.add(R.drawable.drawable1)
        drawableList.add(R.drawable.drawable2)
        drawableList.add(R.drawable.drawable3)
        drawableList.add(R.drawable.drawable4)
        drawableList.add(R.drawable.drawable5)

        val urlList = arrayListOf<String>()
        for (i in 0..10000) {
            urlList.add("https://via.placeholder.com/800x1000/$i/0.png&text=Url+Image+$i")
        }

        val baseItemList = arrayListOf<BaseItem>()
        for (i in 0..10) {
            if (i % 2 == 0) {
                baseItemList.add(BaseItem(imageUrl = urlList[i]))
            } else {
                baseItemList.add(BaseItem(drawableId = drawableList[i % 5]))
            }
        }


        val button = findViewById<Button>(R.id.showDialog)
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                PopopDialogBuilder(this@MainActivity)
                        .setList(urlList)
                        .setHeaderBackgroundColor(android.R.color.holo_blue_light)
                        .setDialogBackgroundColor(R.color.color_dialog_bg)
                        .setCloseDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_close_white_24dp)!!)
                        .setLoadingView(R.layout.loading_view)
                        .setDialogStyle(R.style.DialogStyle)
                        .showThumbSlider(true)
                        .setSliderImageScaleType(ScaleType.FIT_XY)
                        .setSelectorIndicator(R.drawable.sample_indicator_selector)
                        .build()
                        .show();
            }
        })


    }
}
