package com.example.backendintegrationapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.backendintegrationapp.R

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnFetch = findViewById<Button>(R.id.btnFetch)
        val btnSend = findViewById<Button>(R.id.btnSend)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        btnFetch.setOnClickListener { viewModel.fetchPost() }
        btnSend.setOnClickListener { viewModel.sendPost("Task 3", "API Integration Complete.") }

        viewModel.apiResponse.observe(this) { result -> tvResult.text = result }
        viewModel.isLoading.observe(this) { loading ->
            progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }
    }
}
