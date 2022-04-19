package com.dicoding.intermediate.storyapp.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.intermediate.storyapp.model.SessionModel
import com.dicoding.intermediate.storyapp.model.StoryRepository

class AddStoryViewModel(private val repo: StoryRepository) : ViewModel() {
    val isLoading: LiveData<Boolean> = repo.isLoading

    fun getSession(): LiveData<SessionModel> {
        return repo.getSession()
    }
}