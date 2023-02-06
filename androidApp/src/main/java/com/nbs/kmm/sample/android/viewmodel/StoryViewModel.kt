package com.nbs.kmm.sample.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbs.kmm.sample.android.utils.data.Resource
import com.nbs.kmm.sample.android.utils.proceed
import com.nbs.kmm.sample.domain.story.StoryUseCase
import com.nbs.kmm.sample.domain.story.model.GetStoryParam
import com.nbs.kmm.sample.domain.story.model.Story
import kotlinx.coroutines.launch

class StoryViewModel(
    private val useCase: StoryUseCase,
) : ViewModel() {

    private val _storyResult: MutableLiveData<Resource<List<Story>>> = MutableLiveData()
    val storyResult: LiveData<Resource<List<Story>>> get() = _storyResult

    private val _uploadStoryResult: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val uploadStoryResult: LiveData<Resource<Boolean>> get() = _uploadStoryResult

    init {
        _storyResult.value = Resource.default()
        _uploadStoryResult.value = Resource.default()
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

    fun uploadStory(
        file: ByteArray,
        description: String
    ) = viewModelScope.launch {
        _uploadStoryResult.value = Resource.loading()
        proceed(_uploadStoryResult) {
            useCase.uploadStory(file, description)
        }
    }
}