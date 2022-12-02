package com.example.mvvm.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.database.JobDatabase
import com.example.mvvm.databinding.ActivityMainBinding
import com.example.mvvm.network.RetroInstance
import com.example.mvvm.repository.JobRepository
import com.example.mvvm.viewmodel.MainViewModel
import com.example.mvvm.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.statusBarColor = Color.parseColor("#4CAF50")
        supportActionBar?.hide()

        val dao = JobDatabase.invoke(this).jobDao()
        val repository = JobRepository(RetroInstance.retroInstance(), dao)
        val viewModelFactory = MainViewModelFactory(application, repository)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

    }
}