package com.dicoding.asclepius.view.fragments

import android.os.Bundle
import android.os.Parcelable
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
    private lateinit var layoutManager: LinearLayoutManager

    private var isLoading = true
    private var recyclerViewState: Parcelable? = null

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsAdapter = NewsAdapter()
        newsAdapter.setLoadingState(true)

        viewModel.getNews().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    updateUI(isLoading = true, isEmpty = false)
                    binding.progressBar.visibility = View.VISIBLE
                    newsAdapter.setLoadingState(true)
                }

                is Result.Success -> {
                    updateUI(isLoading = false, isEmpty = result.data.isEmpty())
                    binding.progressBar.visibility = View.GONE
                    newsAdapter.setLoadingState(false)
                    newsAdapter.submitList(result.data)

                    // Restore scroll position
                    recyclerViewState?.let {
                        layoutManager.onRestoreInstanceState(it)
                    }
                }

                is Result.Error -> {
                    updateUI(isLoading = false, isEmpty = true)
                    binding.progressBar.visibility = View.VISIBLE
                    Toast.makeText(context, "Data failed to load", Toast.LENGTH_LONG).show()
                    isLoading = false
                }
            }
        }

        binding.recyclerView.apply {
            layoutManager = this@NewsFragment.layoutManager
            adapter = newsAdapter
        }

        // Restore saved state if exist
        savedInstanceState?.let {
            recyclerViewState = it.getParcelable(RECYCLER_VIEW_STATE)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::layoutManager.isInitialized) {
            recyclerViewState = layoutManager.onSaveInstanceState()
            outState.putParcelable(RECYCLER_VIEW_STATE, recyclerViewState)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUI(isLoading: Boolean, isEmpty: Boolean) {
        with(binding) {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            lottieAnimationView.visibility = if (isEmpty && !isLoading) View.VISIBLE else View.GONE
            recyclerView.visibility = if (!isEmpty && !isLoading) View.VISIBLE else View.GONE
        }
    }

    companion object {
        private const val RECYCLER_VIEW_STATE = "recyclerViewState"
    }

}