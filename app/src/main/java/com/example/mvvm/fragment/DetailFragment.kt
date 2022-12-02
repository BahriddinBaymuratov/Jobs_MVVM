package com.example.mvvm.fragment

import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.mvvm.R
import com.example.mvvm.activity.MainActivity
import com.example.mvvm.adapter.mapper.toJobToSave
import com.example.mvvm.databinding.FragmentDetailBinding
import com.example.mvvm.model.Job
import com.example.mvvm.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var job: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = arguments?.getParcelable("job") as? Job
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)
        viewModel = (activity as MainActivity).mainViewModel
        job?.let { saveJob ->
            binding.webView.apply {
                webViewClient = WebViewClient()
                loadUrl(saveJob.url!!)
                settings.apply {
                    javaScriptEnabled = true
                    cacheMode = WebSettings.LOAD_DEFAULT
                    setSupportZoom(false)
                    builtInZoomControls = false
                    displayZoomControls = false
                    textZoom = 100
                    blockNetworkImage = false
                    loadsImagesAutomatically = true
                }
            }
            binding.fabAddFavorite.setOnClickListener {
                viewModel.saveFavoriteJob(saveJob.toJobToSave())
                Snackbar.make(it, "Job Saved", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}