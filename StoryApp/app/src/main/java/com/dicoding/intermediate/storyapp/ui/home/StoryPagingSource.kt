package com.dicoding.intermediate.storyapp.ui.home

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.intermediate.storyapp.service.api.ApiService
import com.dicoding.intermediate.storyapp.service.response.ListStoryItem
import com.dicoding.intermediate.storyapp.utils.SessionPreferences
import kotlinx.coroutines.flow.first
import org.json.JSONObject
import java.net.UnknownHostException

class StoryPagingSource(
    private val pref: SessionPreferences,
    private val apiService: ApiService
) : PagingSource<Int, ListStoryItem>() {

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val token = pref.getSession().first().token

            if (token.isNotEmpty()) {
                val response = apiService.getListStories(token, position, params.loadSize)
                if (response.isSuccessful) {
                    Log.d("Story Paging Source", "Load Result: ${response.body()}")
                    LoadResult.Page(
                        data = response.body()?.listStory ?: emptyList(),
                        prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                        nextKey = if (response.body()?.listStory.isNullOrEmpty()) null else position + 1
                    )
                } else {
                    val jsonObject = response.errorBody()?.string()?.let { JSONObject(it) }
                    val message = jsonObject?.getString("message")
                    Log.e(
                        "getListStories",
                        "Load Error: ${response.message()}, ${response.code()} $message"
                    )
                    LoadResult.Error(Exception("Something went wrong"))
                }
            } else {
                Log.e("Token", "Load Error: $token")
                LoadResult.Error(Exception("Token is Empty"))
            }
        } catch (e: UnknownHostException) {
            Log.e("UnknownHostException", "Load Error: ${e.message}")
            return LoadResult.Error(Exception("No Internet Connection"))
        } catch (e: Exception) {
            Log.e("Exception", "Load Error: ${e.message}")
            return LoadResult.Error(Exception(e.message))
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}