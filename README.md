KM Popup Image Slider for Android
=============
[![Release](https://jitpack.io/v/smhdk/KM-Popup-Image-Slider.svg)](https://jitpack.io/#smhdk/KM-Popup-Image-Slider "![Release](https://jitpack.io/v/smhdk/KM-Popup-Image-Slider.svg)")

You can easyly show image list in dialog with this library.Image list contains url or drawable.  

![Alt Text](https://media.giphy.com/media/4QEGw3pIuzNDD5HP08/giphy.gif)

# Usage

Create a type string,Integer or BaseItem list. This library contains BaseItem class for extended your items.
String list item to be image url and Integer list item to be drawable id.
Option 1 - String list: 
```java
      List<String> url_list = new ArrayList<>();
      url_list.add("https://via.placeholder.com/800x1000/0/0.png");
      url_list.add("https://via.placeholder.com/800x1000/1/1.png");
      url_list.add("https://via.placeholder.com/800x1000/2/2.png");
      url_list.add("https://via.placeholder.com/800x1000/3/3.png");
      url_list.add("https://via.placeholder.com/800x1000/4/4.png");
```

Option 2 - Integer list: 
```java
      List<Integer> drawable_list = new ArrayList<>();
      drawable_list.add(R.drawable.sample1);
      drawable_list.add(R.drawable.sample2);
      drawable_list.add(R.drawable.sample3);
      drawable_list.add(R.drawable.sample4);
      drawable_list.add(R.drawable.sample5);
```

Option 3 - BaseItem list: 
```java
      List<BaseItem> item_list = new ArrayList<>();
      BaseItem item1 = new BaseItem();
      item1.setDrawableId(R.drawable.sample1);

      BaseItem item2 = new BaseItem();
      item2.setImageUrl("https://via.placeholder.com/800x1000/0/0.png");
      
      BaseItem item3 = new BaseItem();
      item3.setDrawableId(R.drawable.sample2);

      item_list.add(item1);
      item_list.add(item2);
      item_list.add(item3);
```
You can create PopupDialogBuilder after creating any list. You can customize the view with parameters

```java
      Dialog dialog = new PopPopopDialogBuilder(activity_context)
                        // Set list like as option1 or option2 or option3
                        .setList(list)
                        // Set dialog header color
                        .setHeaderBackgroundColor(android.R.color.holo_blue_light)
                        // Set dialog background color
                        .setDialogBackgroundColor(R.color.color_dialog_bg)
                        // Set close icon drawable
                        .setCloseDrawable(R.drawable.ic_close_white_24dp)
                        // Set loading view for pager image and preview image
                        .setLoadingView(R.layout.loading_view)
                        // Set dialog style
                        .setDialogStyle(R.style.DialogStyle)
                        // Choose selector type, indicator or thumbnail 
                        .showThumbSlider(true)
                        // Set image scale type for slider image
                        .setSliderImageScaleType(ScaleType.FIT_XY)
                        // Set indicator drawable
                        .setSelectorIndicator(R.drawable.sample_indicator_selector)
			// Enable or disable zoomable
			.setIsZoomable(true)
                        // Build Km Slider Popup Dialog
                        .build()
```
Use this code for show Km Slider Popup Dialog
```java
        dialog.show()
```
You must add internet permission to **AndroidManifest.xml** file for loading image from url.
```xml
    	<uses-permission android:name="android.permission.INTERNET" />
```


# Download

##### Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
##### Step 2. Add the dependency
Add it in app build.gradle
```
dependencies {
	    compile 'com.github.smhdk:KM-Popup-Image-Slider:v1.1.0'
	}
```
  
# Licence

	MIT License

	Copyright (c) 2018 Semih Dik

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
