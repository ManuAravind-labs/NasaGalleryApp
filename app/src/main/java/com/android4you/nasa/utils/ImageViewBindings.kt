package com.android4you.nasa.utils

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.android4you.nasa.R
import com.android4you.space.utils.ImageManager


@BindingAdapter("imageUrl")
fun AppCompatImageView.setImageUrl(url: String?) {
    if (url.isNullOrBlank()) {
        setImageResource(R.drawable.a)
        scaleType = ImageView.ScaleType.FIT_CENTER
    } else {
        ImageManager.setImageUrl(url, this)
    }
}

//@BindingAdapter("android:src")
//fun AppCompatImageView.setImageFromResource(resource: Int) {
//    setImageResource(resource)
//}
//
//@BindingAdapter("tintColor")
//fun AppCompatImageView.setTintColor(color: String) {
//    setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_ATOP)
//}
