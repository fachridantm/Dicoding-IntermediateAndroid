package com.dicoding.intermediate.storyapp.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.intermediate.storyapp.model.StoryRepository
import com.dicoding.intermediate.storyapp.service.response.RegisterResponse
import com.dicoding.intermediate.storyapp.utils.Event
import kotlinx.coroutines.launch

class RegisterViewModel(private val repo: StoryRepository) : ViewModel() {
    val registerResponse: LiveData<RegisterResponse> = repo.registerResponse
    val isLoading: LiveData<Boolean> = repo.isLoading
    val toastText: LiveData<Event<String>> = repo.toastText

    fun postRegister(name: String, email: String, password: String) {
        viewModelScope.launch {
            repo.postRegister(name, email, password)
        }
    }
}