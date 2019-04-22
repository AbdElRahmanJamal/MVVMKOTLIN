package com.kotlinmvvm.UI.newslistUI.adapter

import com.kotlinmvvm.R
import com.kotlinmvvm.data.response.Article
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.news_list_item.*

class NewsListCardItem(
        val article: Article
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.author_name.text = article.author ?: article.source?.name
        viewHolder.news_source.text = article.source?.name
        viewHolder.news_tittle.text = article.title
        viewHolder.news_time.text = article.publishedAt
    }

    override fun getLayout() = R.layout.news_list_item
}