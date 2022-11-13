package com.salt.news.helpers

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.Shimmer.ColorHighlightBuilder
import com.facebook.shimmer.ShimmerDrawable
import com.salt.news.R

class GlideHelper {
    companion object{
        var BASE_ALPHA = 0.6f
        var HIGHLIGHT_ALPHA = 1f

        fun load(context : Context, url : String?, view : ImageView){
            val shimmer = ColorHighlightBuilder()
                .setDuration(1000)
                .setBaseAlpha(BASE_ALPHA)
                .setHighlightAlpha(HIGHLIGHT_ALPHA)
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .setBaseColor(ContextCompat.getColor(context, R.color.grey))
                .setHighlightColor(ContextCompat.getColor(context, R.color.white))
                .build()

            val shimmerPlaceholder = ShimmerDrawable()
            shimmerPlaceholder.setShimmer(shimmer)

            Glide.with(context).load(url).placeholder(shimmerPlaceholder).fitCenter().centerCrop().into(view)
        }
    }
}