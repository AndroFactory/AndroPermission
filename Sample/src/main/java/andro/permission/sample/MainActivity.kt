package andro.permission.sample

import andro.permission.PermissionFactory
import andro.permission.core.GrantResult
import andro.permission.core.PermissionManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var permissionManager : PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permissionManager = PermissionFactory.createPermissionManager(this.applicationContext)
        testCameraPermission()
    }

    private fun testCameraPermission(){
        request.setOnClickListener {
            val cameraPermission = permissionManager.manifestPermission().CameraPermission()
            permissionManager.requestPermission(cameraPermission)
                    ?.subscribe(
                            { grantResult -> onGrantSuccess(grantResult)},
                            { error -> onError(error)}
                    )
        }
    }


    private fun onGrantSuccess(grantResult: GrantResult){
        Toast.makeText(this, grantResult.permission +" : "+grantResult.granted+" : "+grantResult.shouldShowRequestPermission, Toast.LENGTH_LONG).show()
    }

    private fun onError(error: Throwable){
        Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
    }
}
