package com.example.mycheckingtheproduct.app.dto

import android.net.Uri
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.File

open class PhotoProductRealm : RealmObject() {
    @PrimaryKey
    open var id: Long = 0
    open var name: String = "DataObject"
    open var send: RealmList<String>? = null

}

data class Photo(val uri: Uri? = null, val file: File? = null)