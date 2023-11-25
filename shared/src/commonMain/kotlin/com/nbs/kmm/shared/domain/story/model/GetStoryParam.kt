package com.nbs.kmm.shared.domain.story.model

data class GetStoryParam(
    val page: Int,
    val size: Int,
    val isIncludeLocation: Boolean
)