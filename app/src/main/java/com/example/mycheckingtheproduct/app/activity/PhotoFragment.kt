package com.example.mycheckingtheproduct.app.activity

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mycheckingtheproduct.R
import com.example.mycheckingtheproduct.app.util.AndroidUtils
import com.example.mycheckingtheproduct.app.util.StringArg
import com.example.mycheckingtheproduct.app.viewModel.PhotoProductViewModel
import com.example.mycheckingtheproduct.databinding.FragmentPhotoBinding
import com.github.dhaval2404.imagepicker.ImagePicker

class PhotoFragment : Fragment() {

    private val photoRequestCode = 1
    private val cameraRequestCode = 2

    companion object {
        var Bundle.textArg: String? by StringArg
    }

    private val viewModel: PhotoProductViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    private var fragmentBinding: FragmentPhotoBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.menu_new_product, menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.save -> {
//                fragmentBinding?.let {
//                    viewModel.changeContent(it.edit.text.toString())
//                    viewModel.save()
//                    AndroidUtils.hideKeyboard(requireView())
//                }
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPhotoBinding.inflate(
            inflater,
            container,
            false
        )
        fragmentBinding = binding

        binding.pickPhoto.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(2048)
                .galleryOnly()
                .galleryMimeTypes(
                    arrayOf(
                        "image/png",
                        "image/jpeg",
                    )
                )
                .start(photoRequestCode)
        }

        binding.takePhoto.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(2048)
                .cameraOnly()
                .start(cameraRequestCode)
        }
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_new_product, menu)
    }


}