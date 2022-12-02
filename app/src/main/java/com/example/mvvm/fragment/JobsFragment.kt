package com.example.mvvm.fragment

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.activity.MainActivity
import com.example.mvvm.R
import com.example.mvvm.adapter.JobAdapter
import com.example.mvvm.databinding.FragmentJobsBinding
import com.example.mvvm.utils.Resource
import com.example.mvvm.viewmodel.MainViewModel

class JobsFragment : Fragment(R.layout.fragment_jobs) {
    private var _binding: FragmentJobsBinding? = null
    private val binding get() = _binding!!
    private val jobAdapter by lazy { JobAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentJobsBinding.bind(view)

        initViews()
    }

    private fun initViews() {
        val viewModel = (activity as MainActivity).mainViewModel
        binding.rvRemoteJobs.adapter = jobAdapter
        binding.rvRemoteJobs.layoutManager = LinearLayoutManager(requireContext())
        binding.swipeContainer.setOnRefreshListener {
            binding.swipeContainer.isRefreshing = false
        }
        setupViewModel(viewModel)
        jobAdapter.onClick = {
            val bundle = bundleOf("job" to it)
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }
    }

    private fun setupViewModel(viewModel: MainViewModel) {
        viewModel.remoteJobs.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.swipeContainer.isRefreshing = true
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    binding.swipeContainer.isRefreshing = false
                    jobAdapter.submitList(it.data?.jobs)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}