package com.sx.trackdispatch.view

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sx.trackdispatch.R
import com.sx.trackdispatch.databinding.ActivitySplashBinding
import com.util.toast.ToastUtils
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

class SplashActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private lateinit var binding: ActivitySplashBinding

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        "android.permission.ACCESS_BACKGROUND_LOCATION"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_splash
        )
        requestPermissions()
        initView()
    }

    private fun initView() {

    }

    private fun requestPermissions() {
        if (!EasyPermissions.hasPermissions(this, *PERMISSIONS_STORAGE)) {
            EasyPermissions.requestPermissions(
                PermissionRequest.Builder(this, REQUEST_EXTERNAL_STORAGE, *PERMISSIONS_STORAGE)
//                    .setRationale(R.string.rationale_storage)
                    .setPositiveButtonText(getString(R.string.sure))
                    .setNegativeButtonText(getString(R.string.cancel))
                    .setTheme(R.style.PermissionDialog)
                    .build()
            )
        } else {
            authorizedPass()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // EasyPermissions 权限处理请求结果
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    //同意授权
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        authorizedPass()
    }

    private fun authorizedPass(){
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }

    //拒绝授权
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
//            ToastUtils.show(getString(R.string.required_permissions))
//            finish()
        }
    }
}