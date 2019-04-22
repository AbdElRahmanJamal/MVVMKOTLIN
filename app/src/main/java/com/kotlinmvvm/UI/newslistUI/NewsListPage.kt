package com.kotlinmvvm.UI.newslistUI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlinmvvm.FragmentCoroutinseScope
import com.kotlinmvvm.R
import com.kotlinmvvm.UI.newslistUI.adapter.NewsListCardItem
import com.kotlinmvvm.data.ResultState
import com.kotlinmvvm.data.response.Article
import com.kotlinmvvm.data.response.TopHeadLinesNewsResponse
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.news_list_fragment.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class NewsListPage : FragmentCoroutinseScope(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private lateinit var viewModel: NewsListViewModel
    private val newsListViewModelFactory: NewsListViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.title = "Popular Movie"
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = null
        viewModel = ViewModelProviders.of(this, newsListViewModelFactory).get(NewsListViewModel::class.java)

        launch(Main) {
            viewModel.getTopHeadLinesNews("US").observe(this@NewsListPage, Observer {
                if (it != null) {
                    handleResponseState(it)
                }
            })

        }
    }

    private fun handleResponseState(it: ResultState<TopHeadLinesNewsResponse>) {
        when (it) {
            is ResultState.LoadingState -> {
                progress_circular.visibility = View.VISIBLE
                response.text = "Loading"
            }
            is ResultState.DataState -> {
                progress_circular.visibility = View.GONE
                response.visibility = View.GONE
                initNewsListRecView(it.value)
            }
            is ResultState.ErrorState -> {
                progress_circular.visibility = View.GONE
                response.text = it.exception.toString()
            }
        }
    }

    private fun initNewsListRecView(it: TopHeadLinesNewsResponse) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(generateNewsListCardItem(it.articles))
        }
        news_list.apply {
            layoutManager = LinearLayoutManager(this@NewsListPage.context)
            adapter = groupAdapter
        }
        groupAdapter.setOnItemClickListener { item, view ->
            (item as? NewsListCardItem)?.let {
                showNewsDetailsPage(it.article, view)
            }
        }
    }

    private fun showNewsDetailsPage(article: Article, view: View) {
        val goToNewsDetailsPage = NewsListPageDirections.goToDetailsPage(article)
        Navigation.findNavController(view).navigate(goToNewsDetailsPage)
    }

    private fun generateNewsListCardItem(article: List<Article>): MutableList<NewsListCardItem> {
        return MutableList(article.size) {
            NewsListCardItem(article.get(it))
        }
    }
}
