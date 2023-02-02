package com.nbs.kmm.sample.android.persentation.membership.register

import com.nbs.kmm.sample.android.base.SampleBaseActivity
import com.nbs.kmm.sample.android.databinding.ActivityRegisterBinding

class RegisterActivity : SampleBaseActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun initBinding() {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initIntent() {}

    override fun initUI() {

    }

    override fun initAction() {}

    override fun initProses() {}

    override fun initObservers() {}
}