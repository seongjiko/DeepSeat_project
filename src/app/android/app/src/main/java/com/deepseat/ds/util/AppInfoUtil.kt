package com.deepseat.ds.util

import android.content.Context
import android.content.pm.PackageInfo
import android.os.Build

class AppInfoUtil(private val context: Context) {

    val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    val versionName: String
    get() {
        return packageInfo.versionName ?: "0.0.1-SNAPSHOT"
    }
    val buildVersion: String
    get() {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.longVersionCode.toString()
        } else {
            packageInfo.versionCode.toString()
        }
    }

}