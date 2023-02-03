package com.nbs.kmm.sample.android.utils.util

import androidx.fragment.app.FragmentActivity
import com.nbs.kmm.sample.android.utils.AlertFragment
import com.nbs.kmm.sample.domain.base.ApiError
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun FragmentActivity.showNoConnectionAlert(
    errorMessage: String = "Tidak ada koneksi internet. Mohon periksa kembali koneksi internet Anda",
    retryAction: () -> Unit = {}
) {
    val alert = AlertFragment.newInstance(errorMessage)
    alert.setRetryAction(retryAction)
    alert.show(supportFragmentManager, alert.tag)
}

fun FragmentActivity.showServerErrorAlert(
    errorMessage: String = "Terjadi kendala pada server. Mohon coba beberapa saat lagi",
    retryAction: () -> Unit = {}
) {
    val alert = AlertFragment.newInstance(errorMessage)
    alert.setRetryAction(retryAction)
    alert.show(supportFragmentManager, alert.tag)
}

fun FragmentActivity.showClientAlert(
    errorMessage: String = "Terjadi kesalahan pada aplikasi. Mohon coba beberapa saat lagi",
    retryAction: () -> Unit = {}
) {
    val alert = AlertFragment.newInstance(errorMessage)
    alert.setRetryAction(retryAction)
    alert.show(supportFragmentManager, alert.tag)
}

fun FragmentActivity.showTimeoutAlert(
    errorMessage: String = "Koneksi timeout. Mohon coba beberapa saat lagi",
    retryAction: () -> Unit = {}
) {
    val alert = AlertFragment.newInstance(errorMessage)
    alert.setRetryAction(retryAction)
    alert.show(supportFragmentManager, alert.tag)
}

fun FragmentActivity.showUnknownErrorAlert(
    errorMessage: String = "Terjadi kesalahan yang tak terduga",
    retryAction: () -> Unit = {}
) {
    val alert =
        AlertFragment.newInstance(errorMessage)
    alert.setRetryAction(retryAction)
    alert.show(supportFragmentManager, alert.tag)
}

fun FragmentActivity.showErrorAlert(
    cause: Throwable?,
    message: String,
    retryAction: () -> Unit = {}
) {
    when (cause) {
        is ApiError -> {
            when (cause.httpCode) {
                in 400..451 -> showClientAlert(retryAction = retryAction)
                in 500..599 -> showServerErrorAlert(retryAction = retryAction)
                else -> showUnknownErrorAlert(retryAction = retryAction)
            }
        }
        is UnknownHostException -> showNoConnectionAlert(retryAction = retryAction)
        is SocketTimeoutException -> showTimeoutAlert(retryAction = retryAction)
        is IOException -> showUnknownErrorAlert(message,retryAction = retryAction)
        else -> showUnknownErrorAlert(message,retryAction = retryAction)
    }
}
