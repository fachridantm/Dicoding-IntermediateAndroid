package com.dicoding.intermediate.storyapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.intermediate.storyapp.model.SessionModel
import com.dicoding.intermediate.storyapp.model.StoryRepository
import com.dicoding.intermediate.storyapp.service.response.ListStoryItem
import com.dicoding.intermediate.storyapp.service.response.StoriesResponse
import com.dicoding.intermediate.storyapp.utils.Event
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: StoryRepository) : ViewModel() {
    val list: LiveData<StoriesResponse> = repo.list
    val isLoading: LiveData<Boolean> = repo.isLoading
    val toastText: LiveData<Event<String>> = repo.toastText
    val getListStories: LiveData<PagingData<ListStoryItem>> =
        repo.getStories().cachedIn(viewModelScope)

    fun getListStoriesWithLocation(token: String) {
        viewModelScope.launch {
            repo.getListStoriesWithLocation(token)
        }
    }

    fun getSession(): LiveData<SessionModel> {
        return repo.getSession()
    }

    fun logout() {
        viewModelScope.launch {
            repo.logout()
        }
    }
}