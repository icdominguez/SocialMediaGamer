package com.icdominguez.socialmediagamer.login

import android.content.Context
import android.content.Intent
import com.icdominguez.socialmediagamer.base.BaseActivityRouter
import com.icdominguez.socialmediagamer.register.RegisterActivity

class LoginRouter : BaseActivityRouter {
    override fun intent(activity: Context): Intent = Intent(activity, RegisterActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
}