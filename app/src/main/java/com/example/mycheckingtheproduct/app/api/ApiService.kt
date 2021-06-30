package com.example.mycheckingtheproduct.app.api

import androidx.viewbinding.BuildConfig
import com.example.mycheckingtheproduct.app.auth.AppAuth
import com.example.mycheckingtheproduct.app.auth.AuthState
import com.example.mycheckingtheproduct.app.dto.Photo
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


private const val BASE_URL = "http://test.dewival.com/api/"

private val logging = HttpLoggingInterceptor().apply {
    if (BuildConfig.DEBUG) {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

private val okhttp = OkHttpClient.Builder()
    .addInterceptor(logging)
    .addInterceptor { chain ->
        AppAuth.getInstance().authStateFlow.value.token?.let { token ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", token)
                .build()
            return@addInterceptor chain.proceed(newRequest)
        }
        chain.proceed(chain.request())
    }
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(okhttp)
    .build()

interface ProductApiService {

    @Multipart
    @POST("sendfile")
    suspend fun sendPhoto(@Part photo: List<MultipartBody.Part>): Response<List<Photo>>

    @FormUrlEncoded
    @POST("login")
    suspend fun loginUser(
        @Field("login") login: String,
        @Field("pass") pass: String
    ): Response<AuthState>
}

object ProductApi {
    val service: ProductApiService by lazy {
        retrofit.create(ProductApiService::class.java)
    }
}