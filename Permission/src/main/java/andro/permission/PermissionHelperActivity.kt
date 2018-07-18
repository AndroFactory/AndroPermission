package andro.permission

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class PermissionHelperActivity : AppCompatActivity() , ActivityCompat.OnRequestPermissionsResultCallback {

    private val REQUESTCODE = 1542

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(permissions.isNotEmpty()){
            ActivityCompat.requestPermissions(this, permissions, REQUESTCODE)
        }else{
            finish()
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(REQUESTCODE.equals(requestCode)){
            val shouldShowRequestPermission = BooleanArray(permissions.size)
            permissions.forEachIndexed {
                index, permission -> run {
                        shouldShowRequestPermission[index] = ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
                }
            }
            PermissionManagerImpl.populatePermissionsResult(permissions, grantResults, shouldShowRequestPermission)

        }
        finish()
    }

    companion object {
        lateinit var permissions: Array<out String>
    }

}
