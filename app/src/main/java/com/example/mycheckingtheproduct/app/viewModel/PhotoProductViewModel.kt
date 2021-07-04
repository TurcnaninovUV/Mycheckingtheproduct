package com.example.mycheckingtheproduct.app.viewModel

import android.app.Application
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.mycheckingtheproduct.app.auth.AppAuth
import com.example.mycheckingtheproduct.app.dto.Photo
import com.example.mycheckingtheproduct.app.dto.PhotoProductRealm
import com.example.mycheckingtheproduct.app.repository.ProductRepository
import com.example.mycheckingtheproduct.app.repository.ProductRepositoryImpl
import io.realm.RealmList
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.*

private val noPhoto = Photo() // заглушка
private val productRealm = PhotoProductRealm()
private var loginMemory: String = ""
private var passMemory: String =
    "" // сохранение логина и пароля , можно также сохранить в памяти тел


class PhotoProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProductRepository = ProductRepositoryImpl()

    private val _photo = MutableLiveData(noPhoto)
    val photo: LiveData<Photo> // сохранение фото в дату
        get() = _photo

    val authenticated: Boolean
        get() = AppAuth.getInstance().authStateFlow.value.token != null
    // показывает авторизован сейчас юзер или нет

    @RequiresApi(Build.VERSION_CODES.O)
    fun authentication(login: String, pass: String) {
        viewModelScope.launch {
            val byteLogin = login.toByteArray()
            val resultLoginBase64 = String(Base64.getEncoder().encode(byteLogin))
            val bytePass = pass.toByteArray()
            val resultPassBase64 = String(Base64.getEncoder().encode(bytePass))
            productRealm.name = login
            loginMemory = login
            passMemory = pass
            repository.authentication(resultLoginBase64, resultPassBase64)
        }
    }

    fun changePhoto(uri: Uri?, file: File?) {
        _photo.value = Photo(uri, file)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun sendPhoto() {
        if (!authenticated) authentication(loginMemory, passMemory)
        viewModelScope.launch {
            _photo.map {
                productRealm.send?.add(it.toString())
            }
            productRealm.id++
            val multipartList = RealmList<MultipartBody.Part>()
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
            repository.sendInBD(productRealm)
        }
        _photo.value = noPhoto
    }


}
