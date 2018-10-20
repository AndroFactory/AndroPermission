package andro.permission.plugin

import andro.permission.core.PermissionManager
import android.support.v7.app.AppCompatActivity


class PermissionFactory {

    companion object {

        fun createPermissionManager(activity: AppCompatActivity): PermissionManager {
            return PermissionManagerImpl(activity)
        }
    }
}