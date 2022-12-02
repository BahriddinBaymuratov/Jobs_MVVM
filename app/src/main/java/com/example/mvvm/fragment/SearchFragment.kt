package com.example.mvvm.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.R
import com.example.mvvm.activity.MainActivity
import com.example.mvvm.adapter.JobAdapter
import com.example.mvvm.databinding.FragmentSearchBinding
import com.example.mvvm.utils.Resource
import com.example.mvvm.viewmodel.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class SearchFragment : Fragment(R.layout.fragment_search) {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private val jobAdapter by lazy { JobAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        viewModel = (activity as MainActivity).mainViewModel
        initViews()
    }

    private fun initViews() {
        binding.rvSearchJobs.apply {
            adapter = jobAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        searchJob()
        jobAdapter.onClick = {
            val bundle = bundleOf("job" to it)
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }
    }

    private fun searchJob() {
        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                jobAdapter.submitList(emptyList())
                binding.progressBar2.isVisible = false
                delay(600L)
                editable?.let {
                    if (it.toString().isNotBlank()) {
                        viewModel.searchJob(it.toString())
                        viewModel.searchedJobs.observe(viewLifecycleOwner) { result ->
                            when (result) {
                                is Resource.Loading -> {
                                    binding.progressBar2.isVisible = true
                                }
                                is Resource.Error -> {
                                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT)
                                        .show()
                                }
                                is Resource.Success -> {
                                    binding.progressBar2.isVisible = false
                                    jobAdapter.submitList(result.data?.jobs)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}