package com.nbs.kmm.sample.android.presentation.membership.register

import android.content.Context
import android.content.Intent
import com.nbs.kmm.sample.android.R
import com.nbs.kmm.sample.android.base.SampleBaseActivity
import com.nbs.kmm.sample.android.databinding.ActivityRegisterBinding
import com.nbs.kmm.sample.android.utils.data.Resource
import com.nbs.kmm.sample.android.utils.isValidEmail
import com.nbs.kmm.sample.android.utils.isValidPassword
import com.nbs.kmm.sample.android.utils.isValidRePassword
import com.nbs.kmm.sample.android.utils.showToast
import com.nbs.kmm.sample.android.utils.util.showErrorAlert
import com.nbs.kmm.sample.android.viewmodel.MembershipViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : SampleBaseActivity<ActivityRegisterBinding>() {
    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity(Intent(context, RegisterActivity::class.java))
        }
    }

    private val membershipViewModel: MembershipViewModel by viewModel()

    override fun getViewBinding() = ActivityRegisterBinding.inflate(layoutInflater)

    override fun initIntent() {}

    override fun initUI() {}

    override fun initAction() {
        binding.btnRegister.setOnClickListener {
            val userName = binding.edtUserName.text?.trim().toString()
            val email = binding.edtEmail.text?.trim().toString()
            val password = binding.edtPassword.text?.trim().toString()
            val confirmPassword = binding.edtConfirmPassword.text?.trim().toString()

            if (validate(userName, email, password, confirmPassword)) {
                membershipViewModel.register(userName, email, password)
            }
        }
    }

    override fun initProses() {}

    override fun initObservers() {
        membershipViewModel.registerResult.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    hideLoading()
                    showToast("Registrasi Akun Berhasil")
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
        userName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (userName.isEmpty()) binding.tilUserName.error = getString(R.string.error_cannot_empty)
        else binding.tilUserName.error = null

        if (email.isEmpty()) binding.tilEmail.error = getString(R.string.error_cannot_empty)
        else if (!isValidEmail(email)) binding.tilEmail.error =
            getString(R.string.error_email_is_not_valid)
        else binding.tilEmail.error = null

        if (password.isEmpty()) binding.tilPassword.error = getString(R.string.error_cannot_empty)
        if (!isValidPassword(password)) binding.tilPassword.error =
            getString(R.string.error_password_is_not_valid)
        else binding.tilPassword.error = null

        if (confirmPassword.isEmpty()) binding.tilConfirmPassword.error =
            getString(R.string.error_cannot_empty)
        if (!isValidRePassword(password, confirmPassword)) binding.tilConfirmPassword.error =
            getString(R.string.error_confirm_password_is_not_same)
        else binding.tilConfirmPassword.error = null

        return userName.isNotEmpty() && email.isNotEmpty() && isValidEmail(email) && password.isNotEmpty()
                && isValidPassword(password) && isValidRePassword(password, confirmPassword)
    }
}