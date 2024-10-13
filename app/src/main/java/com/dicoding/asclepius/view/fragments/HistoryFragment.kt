package com.dicoding.asclepius.view.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.asclepius.databinding.FragmentHistoryBinding
import com.dicoding.asclepius.helper.Result
import com.dicoding.asclepius.view.adapters.CancerAdapter
import com.dicoding.asclepius.view.viewmodel.MainViewModel
import com.dicoding.asclepius.view.viewmodel.ViewModelFactory

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var cancerAdapter: CancerAdapter
    private lateinit var layoutManager: GridLayoutManager

    private var isLoading = true
    private var recyclerViewState: Parcelable? = null

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        if (context?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = GridLayoutManager(context, 5)
        } else {
            layoutManager = GridLayoutManager(context, 2)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cancerAdapter = CancerAdapter()
        cancerAdapter.setLoadingState(true)

        viewModel.getCancers().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    cancerAdapter.setLoadingState(true)
                    updateUI(true, false)
                }

                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    cancerAdapter.setLoadingState(false)

                    // Check if the data list is empty
                    if (result.data.isEmpty()) {
                        updateUI(false, true)
                    } else {
                        cancerAdapter.submitList(result.data.reversed()) // Pass actual data to the adapter
                        updateUI(false, false)
                        recyclerViewState?.let {
                            layoutManager.onRestoreInstanceState(it)
                        }
                    }
                }

                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, "Data failed to load", Toast.LENGTH_LONG).show()
                    updateUI(false, true)
                    isLoading = false
                }
            }


        }

        binding.recyclerView.apply {
            layoutManager = this@HistoryFragment.layoutManager
            adapter = cancerAdapter
        }

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