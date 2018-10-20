package andro.permission.plugin

import andro.permission.core.ManifestPermission
import android.Manifest

class ManifestPermissionImpl : ManifestPermission {

    override fun CameraPermission(): String {
        return Manifest.permission.CAMERA
    }

    override fun ReadExternalStoragePermission(): String {
        return Manifest.permission.READ_EXTERNAL_STORAGE
    }

    override fun WriteExternalStoragePermission(): String {
        return Manifest.permission.WRITE_EXTERNAL_STORAGE
    }
}