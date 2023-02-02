package com.nbs.kmm.sample.android.presentation.membership.register

import android.content.Context
import android.content.Intent
import com.nbs.kmm.sample.android.base.SampleBaseActivity
import com.nbs.kmm.sample.android.databinding.ActivityRegisterBinding

class RegisterActivity : SampleBaseActivity<ActivityRegisterBinding>() {
    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, RegisterActivity::class.java))
        }
    }

    override fun getViewBinding() = ActivityRegisterBinding.inflate(layoutInflater)

    override fun initIntent() {}

    override fun initUI() {}

    override fun initAction() {}

    override fun initProses() {}
    override fun initObservers() {}
}