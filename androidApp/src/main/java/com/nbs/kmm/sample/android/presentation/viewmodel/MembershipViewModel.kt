package com.nbs.kmm.sample.android.presentation.viewmodel

import com.nbs.kmm.sample.android.base.BaseViewModel
import com.nbs.kmm.sample.domain.membership.MembershipUseCase
import io.reactivex.disposables.CompositeDisposable

class MembershipViewModel(
    private val useCase: MembershipUseCase,
    disposable: CompositeDisposable
) : BaseViewModel(disposable) {
}