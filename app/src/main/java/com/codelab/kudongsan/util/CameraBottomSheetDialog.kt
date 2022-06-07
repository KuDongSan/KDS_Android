package com.codelab.kudongsan.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.codelab.kudongsan.R
import com.codelab.kudongsan.databinding.CameraBottomSheetDialogBinding
import com.codelab.kudongsan.src.main.register.RegisterActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CameraBottomSheetDialog() : BottomSheetDialogFragment() {
    // 바인딩 객체 타입에 ?를 붙여서 null을 허용 해줘야한다. ( onDestroy 될 때 완벽하게 제거를 하기위해 )
    private var mBinding: CameraBottomSheetDialogBinding? = null

    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

//    private val activityResultLauncher = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) {
//        if (it.resultCode == Activity.RESULT_OK) {
//            (activity as RegisterActivity).imageURL = it.data?.data
//            Log.d("img", "${it.data?.data}")
//            dialog?.dismiss()
//        }
//    }

//    private val requestPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()){
//                isGranted: Boolean->
//            if (isGranted) {
//                (activity as RegisterActivity).openGallery()
//                dismiss()
//            }
//            else {
//                (activity as RegisterActivity).showCustomToast("권한을 설정해야 기능을 사용할 수 있습니다.")
//                dismiss()
//            }
//        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = CameraBottomSheetDialogBinding.inflate(inflater, container, false)
        binding.apply {
            cameraBottomSheetDialogCameraLayout.setOnClickListener {
                (activity as RegisterActivity).openCamera()
                dismiss()
            }
            cameraBottomSheetDialogAlbumLayout.setOnClickListener {
//            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                (activity as RegisterActivity).openGallery()
                dismiss()
            }
        }
        Log.d("img", "onCreateView")
        return binding.root
    }

    // LoginBottomSheet 둥글게
    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    // 프래그먼트가 destroy (파괴) 될때..
    override fun onDestroyView() {
        // onDestroyView 에서 binding class 인스턴스 참조를 정리해주어야 한다.
        mBinding = null
        super.onDestroyView()
    }

}