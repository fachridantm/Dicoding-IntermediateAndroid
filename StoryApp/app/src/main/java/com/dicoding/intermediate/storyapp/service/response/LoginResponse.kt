package com.dicoding.intermediate.storyapp.service.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("error")
	val error: Boolean? = true,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("loginResult")
	val loginResult: LoginResult? = null
)

data class LoginResult(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("token")
	val token: String
)
