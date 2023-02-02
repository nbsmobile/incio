package com.nbs.kmm.sample.data.story.model.request

data class GetStoryRequest(
    val page: Int,
    val size: Int,
    val isIncludeLocation: Boolean
)