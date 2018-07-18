package andro.permission

import andro.permission.core.GrantResult
import andro.permission.core.ManifestPermission
import andro.permission.core.PermissionManager
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject


/**
 * Created by pguifo on 23/03/2018.
 */

internal class PermissionManagerImpl internal constructor(private val context: Context) : PermissionManager {

    override fun manifestPermission() = ManifestPermissionImpl()

    override fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun requestPermission(permissions: Array<out String>): Single<Map<String, GrantResult>>? {
        PermissionHelperActivity.permissions = permissions

        val intentone = Intent(context.applicationContext, PermissionHelperActivity::class.java)
        intentone.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intentone)

        return permissionsPublisher
    }

    override fun requestPermission(permission: String): Single<GrantResult>?{
        return requestPermission(arrayOf(permission))!!.map { mapGrantResult -> mapGrantResult.get(permission) }
    }

    companion object {

        val permissionsPublisher = SingleSubject.create<Map<String, GrantResult>>()

        fun populatePermissionsResult(permissions: Array<out String>, grantResults: IntArray, shouldShowRequestPermission : BooleanArray ) {
            var grantResultMap = hashMapOf<String, GrantResult>()
            for ((index,permission) in permissions.withIndex()) {

                grantResultMap.set(
                        permission,
                        GrantResult(
                                permission,
                                grantResults[index] == PackageManager.PERMISSION_GRANTED,
                                shouldShowRequestPermission[index]
                        )
                )
            }
            permissionsPublisher.onSuccess(grantResultMap)
        }

    }
}
