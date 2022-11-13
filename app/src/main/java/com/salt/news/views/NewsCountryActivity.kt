package com.salt.news.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import com.salt.news.SessionManager
import com.salt.news.databinding.ActivityNewsLanguageBinding

class NewsCountryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsLanguageBinding
    private lateinit var session : SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(this)

        initRadioGroup()
    }

    private fun initRadioGroup() {
        binding.radioGroup.check(getRadioId())

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, checkedButton ->
            when (checkedButton) {
                binding.inBtn.id -> {
                    returnToPrevPage("id")
                }
                binding.enBtn.id -> {
                    returnToPrevPage("us")
                }
            }
        }
    }

    private fun returnToPrevPage(country : String){
        session.setNewsCountry(country)

        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun getRadioId() : Int {
        when (session.getNewsCountry()) {
            "id" -> {
                return binding.inBtn.id
            }
            "us" -> {
                return binding.enBtn.id
            }
        }

        return binding.inBtn.id
    }


}