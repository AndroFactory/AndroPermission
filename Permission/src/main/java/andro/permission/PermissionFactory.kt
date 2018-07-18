package andro.permission

import andro.permission.core.PermissionManager
import android.content.Context


class PermissionFactory {

    companion object {

        fun createPermiManager(context: Context): PermissionManager {
            return PermissionManagerImpl(context)
        }
    }
}