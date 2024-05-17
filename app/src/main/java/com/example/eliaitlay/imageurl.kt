package com.example.eliaitlay

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String?) {
    Glide.with(this@loadImage)
        .load(url)
        .into(this)
}

