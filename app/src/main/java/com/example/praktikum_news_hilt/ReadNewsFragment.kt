package com.example.praktikum_news_hilt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.praktikum_news_hilt.databinding.FragmentReadNewsBinding
import com.example.praktikum_news_hilt.presentation.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class ReadNewsFragment : Fragment() {
    private lateinit var binding: FragmentReadNewsBinding
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_read_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReadNewsBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel

        val args: ReadNewsFragmentArgs by navArgs()
        val article = args.selectedArticle
        binding.wvReadArticle.apply {
            webViewClient = WebViewClient()
            if (article.url != "") {
                loadUrl(article.url.toString())
            }
        }
        binding.fabSave.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view, "Berita berhasil disimpan", Snackbar.LENGTH_LONG).show()
        }
    }
}