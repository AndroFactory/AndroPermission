package andro.permission.sample

import andro.permission.PermissionFactory
import andro.permission.core.GrantResult
import andro.permission.core.PermissionManager
import andro.permission.sample.R
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    lateinit var permissionManager : PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionManager = PermissionFactory.createPermissionManager(this.applicationContext)

        val cameraPermission = permissionManager.manifestPermission().CameraPermission()
        permissionManager.requestPermission(cameraPermission)
                ?.subscribe(
                        { grantResult -> onGrantSuccess(grantResult)},
                        { error -> onError(error)}
                )
    }


    private fun onGrantSuccess(grantResult: GrantResult){
        grantResult.toString()
    }

    private fun onError(error: Throwable){
        error.toString()
    }
}
