package com.example.mycheckingtheproduct.app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mycheckingtheproduct.R
import com.example.mycheckingtheproduct.app.auth.AppAuth
import io.realm.Realm
import io.realm.RealmConfiguration

class MainActivity : AppCompatActivity(R.layout.activity_main) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .build()
        Realm.setDefaultConfiguration(config)

        AppAuth.initApp(this)
    }
}