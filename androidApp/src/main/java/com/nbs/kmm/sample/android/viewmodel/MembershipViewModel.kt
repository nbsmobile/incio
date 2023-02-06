package com.nbs.kmm.sample.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbs.kmm.sample.android.utils.data.Resource
import com.nbs.kmm.sample.android.utils.proceed
import com.nbs.kmm.sample.domain.membership.MembershipUseCase
import com.nbs.kmm.sample.domain.membership.model.Login
import com.nbs.kmm.sample.domain.membership.model.RegisterParam
import kotlinx.coroutines.launch

class MembershipViewModel(
    private val useCase: MembershipUseCase,
) : ViewModel() {
    private val _loginResult: MutableLiveData<Resource<Login>> = MutableLiveData()
    val loginResult: LiveData<Resource<Login>> get() = _loginResult

    private val _registerResult: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val registerResult: LiveData<Resource<Boolean>> get() = _registerResult


    init {
        _loginResult.value = Resource.default()
        _registerResult.value = Resource.default()
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _loginResult.value = Resource.loading()
        proceed(_loginResult) {
            useCase.login(email, password)
        }
    }

    fun register(
        username: String,
        email: String,
        password: String,
    ) = viewModelScope.launch {
        _registerResult.value = Resource.loading()
        proceed(_registerResult) {
            useCase.register(RegisterParam(username, email, password))
        }
    }
}