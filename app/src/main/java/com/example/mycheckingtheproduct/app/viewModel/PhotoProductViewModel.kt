package com.example.mycheckingtheproduct.app.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.mycheckingtheproduct.app.dto.Photo
import com.example.mycheckingtheproduct.app.repository.ProductRepository
import com.example.mycheckingtheproduct.app.repository.ProductRepositoryImpl
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

private val noPhoto = Photo() // заглушка

class PhotoProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductRepository = ProductRepositoryImpl()

    private val _photo = MutableLiveData(noPhoto)
    val photo: LiveData<Photo>
        get() = _photo

    fun authentication(login: String, pass: String) {
        viewModelScope.launch {
            repository.authentication(login, pass)
        }
    }

    fun changePhoto(file: File?) {
        _photo.value = Photo(file)
    }

    fun sendPhoto() {
        viewModelScope.launch {
            val multipartList = mutableListOf<MultipartBody.Part>()
            _photo.value?.file.let {
                if (it != null) {
                    multipartList.add(
                        MultipartBody.Part.createFormData(
                            "file", it.name, it.asRequestBody()
                        )
                    )
                }
            }
            repository.sendPhoto(multipartList)
        }
        _photo.value = noPhoto
    }
}
