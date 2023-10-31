package com.dicoding.intermediate.storyapp.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.intermediate.storyapp.model.SessionModel
import com.dicoding.intermediate.storyapp.model.StoryRepository
import com.dicoding.intermediate.storyapp.utils.Event
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val repo: StoryRepository) : ViewModel() {
    val isLoading: LiveData<Boolean> = repo.isLoading
    val toastText: LiveData<Event<String>> = repo.toastText

    fun uploadStory(token: String, file: MultipartBody.Part, description: RequestBody) {
        viewModelScope.launch {
            repo.uploadStory(token, file, description)
        }
    }

    fun getSession(): LiveData<SessionModel> {
        return repo.getSession()
    }
}