package com.example.backendintegrationapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.backendintegrationapp.data.Post
import com.example.backendintegrationapp.data.RetrofitClient
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel : ViewModel() {

    private val _apiResponse = MutableLiveData<String>()
    val apiResponse: LiveData<String> get() = _apiResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchPost() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getPost()
                if (response.isSuccessful && response.body() != null) {
                    val post = response.body()
                    _apiResponse.value = "SUCCESS (GET):\nTitle: ${post?.title}\n\nBody: ${post?.body}"
                } else {
                    _apiResponse.value = "ERROR: Server code ${response.code()}"
                }
            } catch (e: IOException) {
                _apiResponse.value = "NETWORK ERROR: Check your connection."
            } catch (e: Exception) {
                _apiResponse.value = "ERROR: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun sendPost(title: String, body: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val newPost = Post(userId = 1, id = null, title = title, body = body)
                val response = RetrofitClient.apiService.createPost(newPost)
                if (response.isSuccessful && response.body() != null) {
                    val createdPost = response.body()
                    _apiResponse.value = "SUCCESS (POST Id: ${createdPost?.id}):\nTitle: ${createdPost?.title}"
                } else {
                    _apiResponse.value = "ERROR: Code ${response.code()}"
                }
            } catch (e: IOException) {
                _apiResponse.value = "NETWORK ERROR: Server unreachable."
            } catch (e: Exception) {
                _apiResponse.value = "ERROR: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
