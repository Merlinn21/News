package com.salt.news.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.salt.news.databinding.LayoutHeaderBinding
import com.salt.news.databinding.LayoutNewsBinding
import com.salt.news.helpers.DateHelper
import com.salt.news.helpers.GlideHelper
import com.salt.news.models.NewsModel
import java.util.*

class NewsAdapter(private val context: Context, private val onNewsClick: OnNewsClick) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object{
        const val HEADER_VIEW_TYPE = 0;
        const val NEWS_VIEW_TYPE = 1;
    }

    private var newsData : MutableList<NewsModel> = Collections.emptyList<NewsModel?>().toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == HEADER_VIEW_TYPE){
            val binding = LayoutHeaderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            HeaderHolder(binding, onNewsClick)
        } else{
            val binding = LayoutNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            NewsHolder(binding, onNewsClick)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = newsData.get(position)

        if (holder is HeaderHolder){
            holder.bind(model, context)
        } else if(holder is NewsHolder){
            holder.bind(model, context)
        }
    }

    fun setHeadlines(newsData : List<NewsModel>){
        val startSize = this.newsData.size
        this.newsData.addAll(newsData)
        val newSize = this.newsData.size

        notifyItemRangeInserted(startSize, newSize)
    }

    override fun getItemCount(): Int {
        return newsData.size
    }

    override fun getItemViewType(position: Int): Int {
        if(position == 0) return HEADER_VIEW_TYPE
        return NEWS_VIEW_TYPE
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearAdapter(){
        newsData.clear()
        notifyDataSetChanged()
    }

    fun getData(position : Int) : NewsModel{
        return newsData[position]
    }

    class HeaderHolder(private val binding : LayoutHeaderBinding, private val onNewsClick: OnNewsClick) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news : NewsModel, context: Context){
            binding.tvNewsTitle.text = news.title
            binding.tvNewsSource.text = news.source.name
            binding.tvNewsTime.text = DateHelper.getTimeDifference(context, news.publishedAt)

            GlideHelper.load(context, news.urlToImage, binding.ivImgNews)

            binding.root.setOnClickListener(View.OnClickListener {
                onNewsClick.onNewsClick(adapterPosition)
            })
        }
    }

    class NewsHolder(private val binding: LayoutNewsBinding, private val onNewsClick: OnNewsClick) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news : NewsModel, context: Context){
            binding.tvNewsTitle.text = news.title
            binding.tvNewsSource.text = news.source.name
            binding.tvNewsTime.text = DateHelper.getTimeDifference( context, news.publishedAt)

            GlideHelper.load(context, news.urlToImage, binding.ivImgNews)

            binding.root.setOnClickListener(View.OnClickListener {
                onNewsClick.onNewsClick(adapterPosition)
            })
        }


    }

    fun interface OnNewsClick {
        fun onNewsClick(position: Int)
    }

}