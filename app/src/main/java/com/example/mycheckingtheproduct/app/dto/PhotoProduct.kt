package com.example.mycheckingtheproduct.app.dto

import android.net.Uri
import java.io.File

data class PhotoProduct(
    val id: Long,
    val name: String,
    val created: Long,
    val send: List<Photo>
)


data class Photo(val file: File? = null)

//data class PhotoModel(val uri: Uri? = null, val file: File? = null)

