package com.kotlinmvvm.UI.newslistdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.kotlinmvvm.GlideApp
import com.kotlinmvvm.R
import kotlinx.android.synthetic.main.news_details_fragment.*

class NewsDetails : Fragment() {


    private lateinit var viewModel: NewsDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.news_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewsDetailsViewModel::class.java)

        arguments?.let {
            val article = NewsDetailsArgs.fromBundle(it)
            article.articelDetails.source?.name?.let {
                (activity as? AppCompatActivity)?.supportActionBar?.title = it
                (activity as? AppCompatActivity)?.supportActionBar?.subtitle = null
                source_name.text = article.articelDetails.source?.name
            }

            news_description.text = article.articelDetails.description
            article.articelDetails.urlToImage?.let {
                news_image.visibility = View.VISIBLE
                GlideApp.with(this@NewsDetails)
                        .load(it)
                        .into(news_image)
            }
        }
    }

}
