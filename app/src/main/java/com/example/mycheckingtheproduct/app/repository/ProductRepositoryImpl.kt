package com.example.mycheckingtheproduct.app.repository

import com.example.mycheckingtheproduct.app.auth.AppAuth
import com.example.mycheckingtheproduct.app.api.ProductApi
import com.example.mycheckingtheproduct.app.error.ApiError
import com.example.mycheckingtheproduct.app.error.NetworkError
import okhttp3.MultipartBody
import java.io.IOException

class ProductRepositoryImpl : ProductRepository {

    override suspend fun authentication(login: String, pass: String) {
        try {
            val response = ProductApi.service.loginUser(login, pass)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val authState = response.body() ?: throw ApiError(response.code(), response.message())
            authState.token?.let { AppAuth.getInstance().setAuth(authState.id, it) }

        } catch (e: IOException) {
            throw NetworkError
        }
    }

    override suspend fun sendPhoto(photoList: List<MultipartBody.Part>) {
        try {
            val response = ProductApi.service.sendPhoto(photoList)

            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())

        } catch (e: IOException) {
            throw NetworkError
        }

    }
}