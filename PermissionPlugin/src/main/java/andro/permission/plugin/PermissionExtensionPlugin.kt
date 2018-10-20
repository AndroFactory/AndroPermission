package andro.permission.plugin

import andro.permission.core.GrantResult
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray


fun AppCompatActivity.ckeckGrantPermission(permission: String) = GrantResult(permission,
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED,
        ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
)

