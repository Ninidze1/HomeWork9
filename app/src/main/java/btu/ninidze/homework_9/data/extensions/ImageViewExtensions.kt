package btu.ninidze.homework_9.data.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImg(url: String) {

    Glide.with(this.context)
        .load(url)
        .into(this)

}