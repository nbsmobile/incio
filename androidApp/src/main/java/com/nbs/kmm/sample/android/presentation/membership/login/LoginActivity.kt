package com.nbs.kmm.sample.android.presentation.membership.login

import android.content.Context
import android.content.Intent
import com.nbs.kmm.sample.android.base.SampleBaseActivity
import com.nbs.kmm.sample.android.databinding.ActivityLoginBinding
import com.nbs.kmm.sample.android.presentation.main.MainActivity
import com.nbs.kmm.sample.android.presentation.membership.register.RegisterActivity

class LoginActivity : SampleBaseActivity<ActivityLoginBinding>() {
    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    override fun getViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

    override fun initIntent() {}

    override fun initUI() {}

    override fun initAction() {
        with(binding) {
            btnRegister.setOnClickListener {
                RegisterActivity.start(this@LoginActivity)
            }
            btnLogin.setOnClickListener {
                MainActivity.start(this@LoginActivity)
            }
        }
    }

    override fun initProses() {}

    override fun initObservers() {}

}