package com.nbs.kmm.sample.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nbs.kmm.sample.android.base.BaseViewModel
import com.nbs.kmm.sample.android.utils.data.Resource
import com.nbs.kmm.sample.android.utils.proceed
import com.nbs.kmm.sample.domain.story.StoryUseCase
import com.nbs.kmm.sample.domain.story.model.GetStoryParam
import com.nbs.kmm.sample.domain.story.model.Story
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class StoryViewModel(
    private val useCase: StoryUseCase,
    disposable: CompositeDisposable
) : BaseViewModel(disposable) {
    private val _storyResult: MutableLiveData<Resource<List<Story>>> = MutableLiveData()
    val storyResult: LiveData<Resource<List<Story>>> get() = _storyResult


    init {
        _storyResult.value = Resource.default()
    }

    fun fetchStories(
        page: Int,
        size: Int,
        isIncludeLocation: Boolean
    ) = viewModelScope.launch {
        _storyResult.value = Resource.loading()
        proceed(_storyResult) {
            useCase.getAllStories(GetStoryParam(page, size, isIncludeLocation))
        }
    }
}