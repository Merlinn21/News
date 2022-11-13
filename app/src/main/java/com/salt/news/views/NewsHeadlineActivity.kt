package com.salt.news.views

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.salt.news.R
import com.salt.news.SessionManager
import com.salt.news.adapters.NewsAdapter
import com.salt.news.databinding.ActivityNewsHeadlineBinding
import com.salt.news.global.Setting
import com.salt.news.models.CategoryModel
import com.salt.news.viewmodel.NewsViewModels

class NewsHeadlineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsHeadlineBinding
    private lateinit var viewModel : NewsViewModels
    private lateinit var adapter : NewsAdapter
    private lateinit var country : String
    private lateinit var session : SessionManager

    private var isLoading : Boolean = true
    private var page : Int = 1
    private var category : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsHeadlineBinding.inflate(layoutInflater)

        setContentView(binding.root)

        session = SessionManager(this)

        initData()
        initCategoryChip()
        initView()
        initNews()
        initViewModel()
    }

    private fun initData(){
        country = session.getNewsCountry()
    }

    private fun initCategoryChip(){
        for (category in Setting.CATEGORY_LIST){
            val chip = Chip(this)
            val drawable = ChipDrawable.createFromAttributes(this, null, 0, com.google.android.material.R.style.Widget_MaterialComponents_Chip_Choice)
            chip.setChipDrawable(drawable)
            chip.text = category.categoryName
            chip.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.base_color))
            chip.setTextColor(ContextCompat.getColor(this, R.color.white))
            chip.setOnClickListener(View.OnClickListener {
                restartPage(category)
            })

            binding.chipGroup.addView(chip)
        }
    }

    private fun initView(){
        binding.tvLoadMore.setOnClickListener(View.OnClickListener {
            isLoading = true

            binding.loadingBar.visibility = View.VISIBLE
            binding.tvLoadMore.visibility = View.GONE

            viewModel.getHeadlines(country, ++page, category)
        })

        binding.ivIcon.setOnClickListener(View.OnClickListener {
            resultLauncher.launch(Intent(this, NewsCountryActivity::class.java))
        })
    }

    private fun initNews(){
        val linearLayoutManager = LinearLayoutManager(this)
        adapter = NewsAdapter(this, NewsAdapter.OnNewsClick {
            val data = adapter.getData(it)
            val intent = Intent(this, NewsDetailActivity::class.java)
            intent.putExtra("detail", data as java.io.Serializable)
            startActivity(intent)
        })

        binding.rcNews.layoutManager = linearLayoutManager
        binding.rcNews.adapter = adapter
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this)[NewsViewModels::class.java]
        viewModel.observeNewsData().observe(this) { result ->
            checkIsLoading()
            adapter.setHeadlines(result.news)
            checkLastItem(result.total, adapter.itemCount)
        }

        viewModel.getHeadlines(country, page, category)
    }

    private fun checkIsLoading(){
        if (isLoading) {
            isLoading = false
            binding.rcNews.visibility = View.VISIBLE
            binding.tvLoadMore.visibility = View.VISIBLE
            binding.loadingBar.visibility = View.GONE
        }
    }

    private fun checkLastItem(total : Int, current : Int){
        if(total == current){
            binding.tvLoadMore.visibility = View.GONE
        }
    }

    private fun restartPage(categoryData : CategoryModel?){
        if(categoryData != null){
            category = categoryData.id
            binding.tvHeader.text = categoryData.categoryName
        } else{
            binding.tvHeader.text = getString(R.string.headlines)
            category = ""
        }

        binding.loadingBar.visibility = View.VISIBLE
        binding.tvLoadMore.visibility = View.GONE

        isLoading = true
        page = 1
        adapter.clearAdapter()
        country = session.getNewsCountry()

        viewModel.getHeadlines(country, page, category)
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if(country != session.getNewsCountry()){
                restartPage(null)
            }
        }
    }
}