package com.nbs.kmm.sample.android.presentation.rocketlaunch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nbs.kmm.sample.android.base.BaseViewModel
import com.nbs.kmm.sample.android.utils.data.Resource
import com.nbs.kmm.sample.android.utils.proceed
import com.nbs.kmm.sample.domain.rocketlaunch.RocketLaunchUseCase
import com.nbs.kmm.sample.domain.rocketlaunch.model.RocketLaunch
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class RocketLaunchViewModel(
    private val rocketLaunchUseCase: RocketLaunchUseCase,
    disposable: CompositeDisposable
) : BaseViewModel(disposable) {

    private val _rocketLaunchResults: MutableLiveData<Resource<List<RocketLaunch>>> =
        MutableLiveData()

    val rocketLaunchResults: LiveData<Resource<List<RocketLaunch>>> get() = _rocketLaunchResults

    private val _removeRocketLaunchResult: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val removeRocketLaunchResult: LiveData<Resource<Boolean>> get() = _removeRocketLaunchResult

    init {
        _rocketLaunchResults.value = Resource.default()
        _removeRocketLaunchResult.value = Resource.default()
    }

    fun getRocketLaunches() = viewModelScope.launch {
        _rocketLaunchResults.value = Resource.loading()
        _removeRocketLaunchResult.value = Resource.default()
        proceed(_rocketLaunchResults) {
            rocketLaunchUseCase.getRocketLaunches()
        }
    }

    fun removeRocketLaunches() = viewModelScope.launch {
        _removeRocketLaunchResult.value = Resource.loading()
        proceed(_removeRocketLaunchResult, onSuccess = {
            _rocketLaunchResults.value = Resource.default()
        }) {
            rocketLaunchUseCase.removeRocketLaunches()
        }
    }

}