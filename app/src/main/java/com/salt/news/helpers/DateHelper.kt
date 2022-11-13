package com.salt.news.helpers

import android.annotation.SuppressLint
import android.content.Context
import com.salt.news.R
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
class DateHelper {
    companion object {
        fun getTimeDifference(context: Context, date: String) : String{
            val time = Calendar.getInstance().time
            val newsDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(date)

            val diff: Long = time.time - (newsDate?.time ?: time.time)
            val seconds = diff / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24

            return if(hours > 24){
                "$days ${context.getString(R.string.day_diff)}"
            } else{
                "$hours ${context.getString(R.string.hours_diff)}"
            }
        }
    }
}