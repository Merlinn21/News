package com.salt.news.views

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.salt.news.R
import com.salt.news.databinding.ActivityNewsDetailBinding
import com.salt.news.models.NewsModel

class NewsDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsDetailBinding

    private var newsDetail : NewsModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initExtra()
        initWebview()

        binding.tvHeader.text = newsDetail?.source?.name ?: getString(R.string.news_detail)
        binding.ivBack.setOnClickListener(View.OnClickListener { finish() })
    }


    private fun initExtra() {
        newsDetail = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("detail", NewsModel::class.java)
        } else{
            intent.getSerializableExtra("detail") as NewsModel
        }
    }

    private fun initWebview() {
        binding.webview.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                binding.loadingBar.visibility = View.GONE
                binding.webview.visibility = View.VISIBLE
            }
        }

        binding.webview.loadUrl(newsDetail?.url ?: "")
    }

}