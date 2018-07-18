package andro.permission.core

import io.reactivex.Single

/**
 * Created by dev on 29/03/2018.
 */

interface PermissionManager {

    fun manifestPermission() : ManifestPermission

    fun hasPermission(permission : String) : Boolean

    fun requestPermission(permissions : Array<out String>) : Single<Map<String, GrantResult>>?

    fun requestPermission(permission : String) :  Single<GrantResult>?




}
