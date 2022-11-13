package com.salt.news

import android.content.Context

class SessionManager(private val context: Context) {
    private val sharedPreference =  context.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
    private val editor = sharedPreference.edit()

    fun setNewsCountry(lang : String){
        editor.putString("country",lang)

        editor.commit()
    }

    fun getNewsCountry() : String{
        return sharedPreference.getString("country", "id") ?: "id"
    }
}