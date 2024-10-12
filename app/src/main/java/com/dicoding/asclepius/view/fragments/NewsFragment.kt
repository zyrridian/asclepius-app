package com.dicoding.asclepius.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.databinding.FragmentNewsBinding
import com.dicoding.asclepius.helper.Result
import com.dicoding.asclepius.view.adapters.NewsAdapter
import com.dicoding.asclepius.view.viewmodel.MainViewModel
import com.dicoding.asclepius.view.viewmodel.ViewModelFactory

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var newsAdapter: NewsAdapter

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsAdapter = NewsAdapter()

        // Initial shimmer
        newsAdapter.setLoadingState(true)

        viewModel.getNews().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    newsAdapter.setLoadingState(true)
                }

                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    newsAdapter.setLoadingState(false)
                    newsAdapter.submitList(result.data)
                }

                is Result.Error -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Toast.makeText(context, "Data failed to load", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}