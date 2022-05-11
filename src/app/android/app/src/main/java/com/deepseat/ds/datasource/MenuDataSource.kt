package com.deepseat.ds.datasource

import android.content.Context
import com.deepseat.ds.R
import com.deepseat.ds.util.AppInfoUtil
import com.deepseat.ds.vo.MenuVO

class MenuDataSource(private val context: Context) {
    fun getData(): ArrayList<MenuVO> {
        var data = ArrayList<MenuVO>()

        // Application name and icon
        data.add(
            MenuVO(
                "app_info",
                context.getString(R.string.app_name),
                AppInfoUtil(context).versionName,
                false,
                R.drawable.ic_action_deepseat
            )
        )

        // Application build code
        data.add(
            MenuVO(
                "app_build",
                context.getString(R.string.menu_app_build),
                AppInfoUtil(context).buildVersion,
                false,
                R.drawable.ic_info
            )
        )

        // Application build code
        data.add(
            MenuVO(
                "licenses",
                context.getString(R.string.menu_licenses),
                null,
                true,
                R.drawable.ic_license
            )
        )

        // Application build code
        data.add(
            MenuVO(
                "policy",
                context.getString(R.string.menu_policy),
                null,
                true,
                R.drawable.ic_policy
            )
        )

        return data
    }
}