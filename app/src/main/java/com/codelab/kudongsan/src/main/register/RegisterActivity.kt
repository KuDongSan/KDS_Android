package com.codelab.kudongsan.src.main.register

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.codelab.kudongsan.R
import com.codelab.kudongsan.config.ApplicationClass
import com.codelab.kudongsan.config.ApplicationClass.Companion.K_USER_ACCOUNT
import com.codelab.kudongsan.config.BaseActivity
import com.codelab.kudongsan.databinding.ActivityRegisterBinding
import com.codelab.kudongsan.src.main.MainActivity
import com.codelab.kudongsan.src.main.register.models.Address
import com.codelab.kudongsan.src.main.register.models.RegisterRequest
import com.codelab.kudongsan.src.main.register.models.RoomType
import com.codelab.kudongsan.util.CameraBottomSheetDialog
import com.google.firebase.storage.FirebaseStorage
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*


class RegisterActivity : BaseActivity<ActivityRegisterBinding>(ActivityRegisterBinding::inflate),
    RegisterActivityView {

    var salesType: String = ""
    var serviceType: String = ""
    var roomType: String = ""
    var isSuggested: Boolean = false
    var manageCost: String = "N"
    var imageURI: Uri? = null
    var downloadUri: Uri? = null
    var lat = 0.0
    var lng = 0.0

    private val userKey = ApplicationClass.sSharedPreferences.getString(K_USER_ACCOUNT, null)

    enum class SalesType {
        YEARLY_RENT, MONTHLY_RENT, DEALING
    }

    private val cameraBottomSheetDialog = CameraBottomSheetDialog()

    enum class ServiceType {
        ONEROOM, VILLA, OFFICETEL, APARTMENT
    }

    var permissionListenerForCamera: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            activityResultLauncherForCamera.launch(null)
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
            openCamera()
        }
    }

    var permissionListenerForGallery: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.type = "image/*"
            activityResultLauncherForGallery.launch(intent)
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
            openGallery()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            activityRegisterCameraLayout.setOnClickListener {
                cameraBottomSheetDialog.show(supportFragmentManager, cameraBottomSheetDialog.tag)
            }
            activityRegisterBackButton.setOnClickListener {
                onBackPressed()
            }
            activityRegisterSalesTypeButton.setOnClickListener {
                showSalesTypeDialog()
            }
            activityRegisterServiceTypeButton.setOnClickListener {
                showServiceTypeDialog()
            }
            activityRegisterRoomTypeButton.setOnClickListener {
                showRoomTypeDialog()
            }
            activityRegisterManageCostEditText.addTextChangedListener {
                // ?????? ????????? ?????? ??????
                detectKeyboard()
            }
            activityRegisterFinishText.setOnClickListener {
                uploadImageToFirebaseStorage(imageURI)
                showLoadingDialog(this@RegisterActivity)
            }
            activityRegisterNoManageCostTextView.setOnClickListener {
                isSuggested = !isSuggested
                binding.apply {
                    if (isSuggested) {
                        activityRegisterManageCostButtonDefault.visibility = View.INVISIBLE
                        activityRegisterManageCostButtonEvent.visibility = View.INVISIBLE
                        activityRegisterManageCostButtonClicked.visibility = View.VISIBLE
                        manageCost = "Y"
                    }

                    // ?????? ??????
                    else {
                        activityRegisterManageCostButtonDefault.visibility = View.INVISIBLE
                        activityRegisterManageCostButtonEvent.visibility = View.VISIBLE
                        activityRegisterManageCostButtonClicked.visibility = View.INVISIBLE
                        manageCost = "Y"
                    }
                }
            }


        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out)
    }

    private fun showSalesTypeDialog() {
        val items = arrayOf(
            "??????",
            "??????",
            "??????"
        )
        AlertDialog.Builder(this)
            .setItems(items) { _, which ->
                binding.activityRegisterSalesTypeTextView.text = items[which]
                salesType = when (which) {
                    0 -> SalesType.YEARLY_RENT.toString()
                    1 -> SalesType.MONTHLY_RENT.toString()
                    2 -> SalesType.DEALING.toString()
                    else -> "error"
                }
            }
            .show()
    }

    private fun showServiceTypeDialog() {
        val items = arrayOf(
            "??????",
            "??????",
            "????????????",
            "?????????"
        )
        AlertDialog.Builder(this)
            .setItems(items) { _, which ->
                binding.activityRegisterServiceTypeTextView.text = items[which]
                serviceType = when (which) {
                    0 -> ServiceType.ONEROOM.toString()
                    1 -> ServiceType.VILLA.toString()
                    2 -> ServiceType.OFFICETEL.toString()
                    3 -> ServiceType.APARTMENT.toString()
                    else -> "error"
                }
            }
            .show()
    }

    private fun showRoomTypeDialog() {
        val items = arrayOf(
            "????????? ??????",
            "????????? ??????",
            "????????? ??????",
            "??????",
            "?????????+",
            "??????+"
        )
        AlertDialog.Builder(this)
            .setItems(items) { _, which ->
                binding.activityRegisterRoomTypeTextView.text = items[which]
                roomType = when (which) {
                    0 -> "01"
                    1 -> "02"
                    2 -> "03"
                    3 -> "04"
                    4 -> "05"
                    5 -> "06"
                    else -> "error"
                }
            }
            .show()
    }

    private fun detectKeyboard() {
        when (binding.activityRegisterManageCostEditText.text.toString().length) {

            0 -> {
                binding.apply {
                    activityRegisterManageCostWonText.setTextColor(Color.GRAY)
                    activityRegisterNoManageCostTextView.setTextColor(Color.GRAY)
                    activityRegisterManageCostWonText.alpha = 0.6F
                    activityRegisterNoManageCostTextView.alpha = 0.6F
                    activityRegisterManageCostButtonDefault.visibility = View.VISIBLE
                    activityRegisterManageCostButtonEvent.visibility = View.INVISIBLE
                    activityRegisterManageCostButtonClicked.visibility = View.INVISIBLE
                }
            }

            in 1..10 -> {
                binding.apply {
                    activityRegisterManageCostWonText.setTextColor(Color.BLACK)
                    activityRegisterNoManageCostTextView.setTextColor(Color.BLACK)
                    activityRegisterManageCostWonText.alpha = 0.9F
                    activityRegisterNoManageCostTextView.alpha = 0.9F
                    if (isSuggested) {
                        activityRegisterManageCostButtonDefault.visibility = View.INVISIBLE
                        activityRegisterManageCostButtonEvent.visibility = View.INVISIBLE
                        activityRegisterManageCostButtonClicked.visibility = View.VISIBLE
                        manageCost = "Y"
                    } else {
                        activityRegisterManageCostButtonDefault.visibility = View.INVISIBLE
                        activityRegisterManageCostButtonEvent.visibility = View.VISIBLE
                        activityRegisterManageCostButtonClicked.visibility = View.INVISIBLE
                        manageCost = "N"
                    }
                }
            }
        }
    }

    fun openCamera() {
        TedPermission.with(this)
            .setPermissionListener(permissionListenerForCamera)
            .setRationaleMessage("?????? ????????? ???????????? ???????????? ????????? ???????????????.")
            .setDeniedMessage("[??????] > [??????] ?????? ????????? ????????? ??? ????????????.")
            .setPermissions(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .check()
    }

    fun openGallery() {
        TedPermission.with(this)
            .setPermissionListener(permissionListenerForGallery)
            .setRationaleMessage("?????? ????????? ???????????? ???????????? ????????? ???????????????.")
            .setDeniedMessage("[??????] > [??????] ?????? ????????? ????????? ??? ????????????.")
            .setPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .check()
    }

    private val activityResultLauncherForCamera = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) {
        binding.apply {
            activityRegisterCardView.visibility = View.VISIBLE
            Glide.with(this@RegisterActivity).load(it).into(activityRegisterCameraImageReal)
            imageURI = getImageUri(this@RegisterActivity, it)
        }
    }

    private fun getImageUri(context: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }

    private val activityResultLauncherForGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            Log.d("img", "${it.data?.data}")
            binding.apply {
                activityRegisterCardView.visibility = View.VISIBLE
                Glide.with(this@RegisterActivity).load(it.data?.data)
                    .into(activityRegisterCameraImageReal)
                imageURI = it.data?.data
            }
        }
    }

    //Firebase Storage??? ???????????? ????????? ?????? ??????.
    @SuppressLint("SimpleDateFormat")
    private fun uploadImageToFirebaseStorage(uri: Uri?) {
        val storage: FirebaseStorage = FirebaseStorage.getInstance()   //FirebaseStorage ???????????? ??????
        //?????? ?????? ??????.
        Log.d("test", "$userKey")
        Log.d("test", "$uri")
        val fileName = "${userKey}_IMAGE_${SimpleDateFormat("yyyymmdd_HHmmss").format(Date())}.png"
        //?????? ?????????, ????????????, ??????, ??????????????? ???????????? ?????? ??????????????? ?????? ?????? ????????? ??????.
        //????????? ???????????? ????????? ???????????? ??????????????? ??? ??? ??????.
        val imagesRef = storage.reference.child("${userKey}/")
            .child(fileName)    //?????? ?????? ??????/images/userId/${fileName}
        //????????? ?????? ?????????
        imagesRef.putFile(uri!!).addOnSuccessListener {
            dismissLoadingDialog()
            showCustomToast("????????? ??????!")
            // ????????? ?????? ????????????
            imagesRef.downloadUrl.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    downloadUri = task.result

                    // ?????? ?????? ?????? ????????? ?????? 
                    // ?????? ??????????????? ???????????? ?????????...
                    binding.apply {
                        RegisterService(this@RegisterActivity).tryPostRegister(
                            RegisterRequest(
                                title = activityRegisterTitleEditText.text.toString(),
                                description = activityRegisterContentEditText.text.toString(),
                                imageThumbnail = downloadUri.toString(),
                                address = Address(jibunAddress = "", address1 = "", address2 = "", address3 = ""),
                                area = activityRegisterAreaEditText.text.toString().toDouble() * 3.306,
                                manageCost = activityRegisterManageCostEditText.text.toString().toDouble(),
                                roomType = RoomType(roomType = roomType, roomTypeCode = roomType),
                                salesType = salesType,
                                serviceType = serviceType,
                                deposit = activityRegisterDepositEditTextView.text.toString().toInt(),
                                monthlyRentPrice = activityRegisterMonthlyRentEditTextView.text.toString(),
                                lat = lat,
                                lng = lng
                            )
                        )
                    }

                } else {
                    showCustomToast("????????? ?????? ???????????? ??????")
                }
            }
        }.addOnFailureListener {
            dismissLoadingDialog()
            println(it)
            showCustomToast("????????? ??????")
        }
    }

    override fun onPostRegisterSuccess(responseCode: Int) {
        if(responseCode==200) {
            showCustomToast("??? ????????? ?????? ??????")
            onBackPressed()
            // ?????? ??? ???????????????????????? ????????? ????????? ??????????????????
            // ???????????????????????? ????????? GET api??? ??????????????????..
        }
        else {
            showCustomToast("?????? ???????")
        }
    }

    override fun onPostRegisterFailure(message: String) {
        showCustomToast("?????? : $message")
    }

}