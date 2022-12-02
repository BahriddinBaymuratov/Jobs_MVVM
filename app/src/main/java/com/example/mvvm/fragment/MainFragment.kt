package com.example.mvvm.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.mvvm.R
import com.example.mvvm.databinding.FragmentMainBinding
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems

class MainFragment : Fragment(R.layout.fragment_main) {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        initViews()
    }

    private fun initViews() {
        val adapter = FragmentPagerItemAdapter(
            childFragmentManager,
            FragmentPagerItems.with(requireContext())
                .add("Jobs", JobsFragment::class.java)
                .add("Search", SearchFragment::class.java)
                .add("Saved", SavedFragment::class.java)
                .create()
        )
        binding.viewPager.adapter = adapter
        binding.tabLayout.setViewPager(binding.viewPager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}