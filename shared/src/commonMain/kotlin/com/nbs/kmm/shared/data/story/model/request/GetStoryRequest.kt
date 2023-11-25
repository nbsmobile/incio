package com.nbs.kmm.shared.data.story.model.request

data class GetStoryRequest(
    val page: Int,
    val size: Int,
    val isIncludeLocation: Boolean
)