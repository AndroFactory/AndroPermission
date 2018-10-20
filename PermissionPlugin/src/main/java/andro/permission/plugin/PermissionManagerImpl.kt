package andro.permission.plugin

import andro.permission.core.GrantResult
import andro.permission.core.PermissionManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject


/**
 * Created by pguifo on 23/03/2018.
 */

internal class PermissionManagerImpl internal constructor(private val activity: AppCompatActivity) : PermissionManager {

    private val permissionsPublisher = SparseArray<(Map<String, GrantResult>) -> Unit>()

    override fun ckeckGrantPermission(permission: String) = GrantResult(permission,
            ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED,
            ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
    )

    override fun requestPermissions(requestCode : Int, permissions: Array<out String>, publisher: (Map<String, GrantResult>) -> Unit){
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
        permissionsPublisher.put(requestCode, publisher)
    }



    override fun requestPermission(requestCode : Int, permission: String, publisher: (GrantResult) -> Unit){
        requestPermissions(requestCode, arrayOf(permission)) { mapGrantResult ->
            publisher(mapGrantResult[permission]!!)
        }
    }

    fun doOnRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        permissionsPublisher[requestCode]?.let { publisher ->
            val grantResultMap = hashMapOf<String, GrantResult>()
            permissions.forEachIndexed { index, permission ->
                grantResultMap[permission] = GrantResult(
                        permission,
                        grantResults[index] == PackageManager.PERMISSION_GRANTED,
                        ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
                )
            }
            publisher(grantResultMap)
        }
    }

    companion object {

        val permissionsPublisher = SingleSubject.create<Map<String, GrantResult>>()

        fun populatePermissionsResult(permissions: Array<out String>, grantResults: IntArray, shouldShowRequestPermission : BooleanArray ) {
            val grantResultMap = hashMapOf<String, GrantResult>()
            for ((index,permission) in permissions.withIndex()) {
                grantResultMap[permission] = GrantResult(
                        permission,
                        grantResults[index] == PackageManager.PERMISSION_GRANTED,
                        shouldShowRequestPermission[index]
                )
            }
            permissionsPublisher.onSuccess(grantResultMap)
        }

    }
}
