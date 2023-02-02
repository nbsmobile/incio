package com.nbs.kmm.sample.android.presentation.membership.login

import android.content.Context
import android.content.Intent
import com.nbs.kmm.sample.android.R
import com.nbs.kmm.sample.android.base.SampleBaseActivity
import com.nbs.kmm.sample.android.databinding.ActivityLoginBinding
import com.nbs.kmm.sample.android.presentation.main.MainActivity
import com.nbs.kmm.sample.android.presentation.membership.register.RegisterActivity
import com.nbs.kmm.sample.android.utils.data.Resource
import com.nbs.kmm.sample.android.utils.isValidEmail
import com.nbs.kmm.sample.android.utils.isValidPassword
import com.nbs.kmm.sample.android.utils.showToast
import com.nbs.kmm.sample.android.utils.util.showErrorAlert
import com.nbs.kmm.sample.android.viewmodel.MembershipViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : SampleBaseActivity<ActivityLoginBinding>() {
    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    private val vm: MembershipViewModel by viewModel()

    override fun getViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

    override fun initIntent() {}

    override fun initUI() {}

    override fun initAction() {
        with(binding) {
            btnRegister.setOnClickListener {
                RegisterActivity.start(this@LoginActivity)
            }
            btnLogin.setOnClickListener {
                val email = binding.edtEmail.text?.trim().toString()
                val password = binding.edtPassword.text?.trim().toString()

                if (validate(email, password)) {
                    vm.login( email, password)
                }
            }
        }
    }

    override fun initProses() {}

    override fun initObservers() {
        vm.loginResult.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    hideLoading()
                    showToast("Login Berhasil")
                    MainActivity.start(this)
                    finish()
                }

                is Resource.Failure -> {
                    hideLoading()
                    showErrorAlert(it.throwable, it.message.toString())
                }
                else -> {}
            }
        }
    }

    private fun validate(
        email: String,
        password: String,
    ): Boolean {
        if (email.isEmpty()) binding.tilEmail.error = getString(R.string.error_cannot_empty)
        else if (!isValidEmail(email)) binding.tilEmail.error =
            getString(R.string.error_email_is_not_valid)
        else binding.tilEmail.error = null

        if (password.isEmpty()) binding.tilPassword.error = getString(R.string.error_cannot_empty)
        if (!isValidPassword(password)) binding.tilPassword.error =
            getString(R.string.error_password_is_not_valid)
        else binding.tilPassword.error = null

        return email.isNotEmpty() && isValidEmail(email) && password.isNotEmpty()
                && isValidPassword(password)
    }
}