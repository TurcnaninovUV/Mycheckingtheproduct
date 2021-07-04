package com.example.mycheckingtheproduct.app.repository


import com.example.mycheckingtheproduct.app.dto.PhotoProductRealm
import io.realm.RealmList
import okhttp3.MultipartBody

interface ProductRepository {

    suspend fun authentication(login: String, pass: String)
    suspend fun sendPhoto(photoList: RealmList<MultipartBody.Part>)
    suspend fun sendInBD(photoProduct: PhotoProductRealm)

}