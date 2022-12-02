package com.example.mvvm.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.R
import com.example.mvvm.activity.MainActivity
import com.example.mvvm.adapter.JobToSaveAdapter
import com.example.mvvm.databinding.FragmentSavedBinding
import com.example.mvvm.model.JobToSave
import com.example.mvvm.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar


class SavedFragment : Fragment(R.layout.fragment_saved) {
    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!
    private val jobToSaveAdapter by lazy { JobToSaveAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSavedBinding.bind(view)
        viewModel = (activity as MainActivity).mainViewModel
        initViews()
    }

    private fun initViews() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = jobToSaveAdapter
        }
        viewModel.getAllLocalJobs().observe(viewLifecycleOwner){
            jobToSaveAdapter.submitList(it)
            binding.cardNoAvailable.isVisible = it.isEmpty()
        }
        jobToSaveAdapter.onDeleteClick ={
            deleteJob(it)
        }
        jobToSaveAdapter.onClick ={
//            val bundle = bundleOf("jobs" to it)
//            findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }

    }
    private fun deleteJob(it: JobToSave){
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Delete")
                setMessage("Do you want to delete ${it.title}")
            setPositiveButton("Yes") {di, _ ->
                viewModel.deleteJob(it)
                di.dismiss()
                Snackbar.make(binding.root,"Job deleted", Snackbar.LENGTH_SHORT).show()
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}