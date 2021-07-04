package com.example.mycheckingtheproduct.app.activity

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mycheckingtheproduct.R
import com.example.mycheckingtheproduct.app.util.AndroidUtils
import com.example.mycheckingtheproduct.app.viewModel.PhotoProductViewModel
import com.example.mycheckingtheproduct.databinding.FragmentSignInBinding

class FragmentSignIn : Fragment() {

    private val viewModelAuth: PhotoProductViewModel by viewModels(ownerProducer = ::requireParentFragment)


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding.buttonSignIn.setOnClickListener {
            val login = binding.login.text?.trim().toString()
            val pass = binding.password.text?.trim().toString()
            viewModelAuth.authentication(login, pass)
            AndroidUtils.hideKeyboard(it)
            findNavController().navigate(R.id.action_fragmentSignIn_to_photoFragment)
        }
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigateUp()
        }

        return binding.root
    }
}