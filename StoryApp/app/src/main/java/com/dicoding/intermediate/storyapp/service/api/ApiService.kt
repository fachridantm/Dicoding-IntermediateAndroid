package com.dicoding.intermediate.storyapp.service.api

import com.dicoding.intermediate.storyapp.service.response.AddStoryResponse
import com.dicoding.intermediate.storyapp.service.response.LoginResponse
import com.dicoding.intermediate.storyapp.service.response.RegisterResponse
import com.dicoding.intermediate.storyapp.service.response.StoriesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun postRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("stories")
    fun getListStories(
        @Header("Authorization") token: String
    ):Call<StoriesResponse>

    @Multipart
    @POST("stories")
    fun postStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ):Call<AddStoryResponse>
}