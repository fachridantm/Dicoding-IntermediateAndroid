package com.dicoding.intermediate.storyapp.service.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class StoriesResponse(

	@field:SerializedName("listStory")
	val listStory: List<ListStoryItem>? = emptyList(),

	@field:SerializedName("error")
	val error: Boolean? = true,

	@field:SerializedName("message")
	val message: String? = null
)

@Parcelize
data class ListStoryItem(

	@field:SerializedName("photoUrl")
	val photo: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("lon")
	val lon: Double,

	@field:SerializedName("lat")
	val lat: Double,

	@field:SerializedName("id")
	val id: String
) : Parcelable
