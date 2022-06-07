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
                // 글자 입력시 색상 변화
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

                    // 일단 대충
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
            "전세",
            "월세",
            "매매"
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
            "원룸",
            "빌라",
            "오피스텔",
            "아파트"
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
            "오픈형 원룸",
            "분리형 원룸",
            "복층형 원룸",
            "투룸",
            "쓰리룸+",
            "포룸+"
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
            .setRationaleMessage("앱의 기능을 사용하기 위해서는 권한이 필요합니다.")
            .setDeniedMessage("[설정] > [권한] 에서 권한을 허용할 수 있습니다.")
            .setPermissions(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .check()
    }

    fun openGallery() {
        TedPermission.with(this)
            .setPermissionListener(permissionListenerForGallery)
            .setRationaleMessage("앱의 기능을 사용하기 위해서는 권한이 필요합니다.")
            .setDeniedMessage("[설정] > [권한] 에서 권한을 허용할 수 있습니다.")
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

    //Firebase Storage에 이미지를 업로드 하는 함수.
    @SuppressLint("SimpleDateFormat")
    private fun uploadImageToFirebaseStorage(uri: Uri?) {
        val storage: FirebaseStorage = FirebaseStorage.getInstance()   //FirebaseStorage 인스턴스 생성
        //파일 이름 생성.
        Log.d("test", "$userKey")
        Log.d("test", "$uri")
        val fileName = "${userKey}_IMAGE_${SimpleDateFormat("yyyymmdd_HHmmss").format(Date())}.png"
        //파일 업로드, 다운로드, 삭제, 메타데이터 가져오기 또는 업데이트를 하기 위해 참조를 생성.
        //참조는 클라우드 파일을 가리키는 포인터라고 할 수 있음.
        val imagesRef = storage.reference.child("${userKey}/")
            .child(fileName)    //기본 참조 위치/images/userId/${fileName}
        //이미지 파일 업로드
        imagesRef.putFile(uri!!).addOnSuccessListener {
            dismissLoadingDialog()
            showCustomToast("업로드 성공!")
            // 이미지 파일 가져오기
            imagesRef.downloadUrl.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    downloadUri = task.result

                    // 우선 주소 빼고 나머지 구현 
                    // 주소 입력방식은 레이아웃 생각중...
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
                    showCustomToast("이미지 파일 다운로드 에러")
                }
            }
        }.addOnFailureListener {
            dismissLoadingDialog()
            println(it)
            showCustomToast("업로드 실패")
        }
    }

    override fun onPostRegisterSuccess(responseCode: Int) {
        if(responseCode==200) {
            showCustomToast("내 부동산 등록 성공")
            onBackPressed()
            // 등록 후 메인액티비티에서 등록한 매물이 보여야하므로
            // 메인액티비티에서 새로운 GET api가 필요해보인다..
        }
        else {
            showCustomToast("오류 발생?")
        }
    }

    override fun onPostRegisterFailure(message: String) {
        showCustomToast("오류 : $message")
    }

}