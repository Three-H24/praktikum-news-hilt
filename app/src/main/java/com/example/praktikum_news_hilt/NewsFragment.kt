package com.example.praktikum_news_hilt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.praktikum_news_hilt.data.util.Resource
import com.example.praktikum_news_hilt.databinding.FragmentNewsBinding
import com.example.praktikum_news_hilt.presentation.adapter.NewsAdapter
import com.example.praktikum_news_hilt.presentation.viewModel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    private var country = "id"
    private var page = 1
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter

        newsAdapter.setOnItemListener { article ->
            val bundle = Bundle().apply {
                putSerializable("selected_article", article)
            }
            findNavController().navigate(
                R.id.action_newsFragment_to_readNewsFragment,
                bundle
            )
        }
        initRecyclerView()
        viewNewsList()
        setSearchView()
    }

    private fun initRecyclerView() {
        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@NewsFragment.onScrollListener)
        }
    }

    private fun showProgressBar() {
        isLoading = true
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        isLoading = false
        binding.progressBar.visibility = View.GONE
    }

    private fun viewNewsList() {
        viewModel.getNewsHeadlines(country, page)
        viewModel.newsHeadlines.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles?.toList())
                        if (it.totalResults?.rem(20) == 0) {
                            pages = it.totalResults.div(20)
                        } else {
                            pages = it.totalResults!!.div(20) + 1
                        }
                        isLastPage = (page == pages)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = binding.rvNews.layoutManager as LinearLayoutManager
            val sizeOfCurrentList = layoutManager.itemCount
            val visibleItem = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = ((topPosition + visibleItem) >= sizeOfCurrentList)
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling

            if (shouldPaginate == true) {
                page++
                viewModel.getNewsHeadlines(country, page)
                isScrolling = false
            }
        }
    }

    // Search News
    private fun setSearchView() {
        binding.svNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // Ketika tombol search di keyboard di click
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.getSearchNews(country, page, p0.toString())
                viewSearchNewsList()
                return false
            }

            // Ketika terjadi perubahan text pada kolom search
            override fun onQueryTextChange(p0: String?): Boolean {
                MainScope().launch {
                    delay(1000)
                    viewModel.getSearchNews(country, page, p0.toString())
                    viewSearchNewsList()
                }
                return false
            }

        })

        binding.svNews.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                initRecyclerView()
                viewNewsList()
                return false
            }
        })
    }

    private fun viewSearchNewsList() {
        viewModel.searchNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles?.toList())
                        if (it.totalResults?.rem(20) == 0) {
                            pages = it.totalResults.div(20)
                        } else {
                            pages = it.totalResults!!.div(20) + 1
                        }
                        isLastPage = (page == pages)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }
}
