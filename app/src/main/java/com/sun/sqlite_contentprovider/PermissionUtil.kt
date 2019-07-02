package com.sun.sqlite_contentprovider

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtil {
    val storagePermissions = arrayOf(
            Manifest.permission.READ_SMS)

    fun isGrantResultsGranted(grantResults: IntArray): Boolean {
        if (grantResults.isNotEmpty()) {
            grantResults.forEach {
                if (it != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
            return true
        } else {
            return false
        }
    }

    fun isPermissionsGranted(context: Context, permissions: Array<String>): Boolean {
        permissions.forEach {
            if (!isPermissionGranted(context, it)) {
                return false
            }
        }
        return true
    }

    fun requestStoragePermission(activity: Activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_SMS)) {
            activity.showMessageDialog(activity.getString(R.string.alert_permissions_rationale)) {
                ActivityCompat.requestPermissions(activity, storagePermissions, RequestCode.PERMISSION_SMS)
            }
        } else {
            ActivityCompat.requestPermissions(activity, storagePermissions, RequestCode.PERMISSION_SMS)
        }
    }

    // ---------------------------------------------------------------------------------------------
    private fun isPermissionGranted(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

}


