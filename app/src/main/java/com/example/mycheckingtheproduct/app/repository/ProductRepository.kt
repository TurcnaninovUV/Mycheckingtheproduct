package com.example.mycheckingtheproduct.app.repository


import okhttp3.MultipartBody

interface ProductRepository {

    suspend fun authentication(login: String, pass: String)
    suspend fun sendPhoto(photoList: List<MultipartBody.Part>)

}