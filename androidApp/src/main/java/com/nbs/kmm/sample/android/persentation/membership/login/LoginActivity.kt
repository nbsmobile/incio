package com.nbs.kmm.sample.android.persentation.membership.login

import com.nbs.kmm.sample.android.base.SampleBaseActivity
import com.nbs.kmm.sample.android.databinding.ActivityLoginBinding

class LoginActivity : SampleBaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun initBinding() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initIntent() {}

    override fun initUI() {

    }

    override fun initAction() {}

    override fun initProses() {}

    override fun initObservers() {}
}