package com.example.praktikum_news_hilt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.praktikum_news_hilt.databinding.FragmentSavedBinding
import com.example.praktikum_news_hilt.presentation.adapter.NewsAdapter
import com.example.praktikum_news_hilt.presentation.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class SavedFragment : Fragment() {
    private lateinit var binding: FragmentSavedBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter

        newsAdapter.setOnItemListener { article ->
            val bundle = Bundle().apply {
                putSerializable("selected_article", article)
            }
            findNavController().navigate(
                R.id.action_savedFragment_to_readNewsFragment,
                bundle
            )
        }
        initRecyclerView()
        viewModel.getSavedNews().observe(viewLifecycleOwner) { articles ->
            newsAdapter.differ.submitList(articles)
        }

        initSwipeLeftToDelete(view)
    }

    private fun initRecyclerView() {
        binding.rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun initSwipeLeftToDelete(view: View) {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(view, "Berita berhasil dihapus", Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("Batal") {
                            viewModel.saveArticle(article)
                        }
                    }.show()
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.rvSavedNews)
    }
}