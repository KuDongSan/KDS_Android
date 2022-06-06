package com.codelab.kudongsan.util

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import com.codelab.kudongsan.R
import com.codelab.kudongsan.src.main.register.RegisterActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CameraBottomSheetDialogTemp() : BottomSheetDialogFragment() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){
                isGranted: Boolean->
            if (isGranted) {
                (activity as RegisterActivity).openGallery()
                dismiss()
            }
            else {
                (activity as RegisterActivity).showCustomToast("권한을 설정해야 기능을 사용할 수 있습니다.")
                dismiss()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.camera_bottom_sheet_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ConstraintLayout>(R.id.camera_bottom_sheet_dialog_album_layout).setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
}