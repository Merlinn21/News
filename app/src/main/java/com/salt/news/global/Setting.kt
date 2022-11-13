package com.salt.news.global

import com.salt.news.models.CategoryModel

object Setting {
    const val PAGE_SIZE = 5

    val CATEGORY_LIST = listOf<CategoryModel>(
        CategoryModel("business", "Business"),
        CategoryModel("entertainment", "Entertainment"),
        CategoryModel("health", "Health"),
        CategoryModel("science", "Science"),
        CategoryModel("sport", "Sport"),
        CategoryModel("technology", "Technology")
    )
}