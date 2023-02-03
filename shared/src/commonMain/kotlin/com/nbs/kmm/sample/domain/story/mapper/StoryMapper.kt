package com.nbs.kmm.sample.domain.story.mapper

import com.nbs.kmm.sample.data.story.model.request.GetStoryRequest
import com.nbs.kmm.sample.data.story.model.response.StoryResponse
import com.nbs.kmm.sample.domain.story.model.GetStoryParam
import com.nbs.kmm.sample.domain.story.model.Story
import com.nbs.kmm.sample.utils.ext.orZero

fun GetStoryParam.toRequest(): GetStoryRequest {
    return GetStoryRequest(
        page = page,
        size = size,
        isIncludeLocation = isIncludeLocation
    )
}

fun StoryResponse.toDomain(): Story {
    return Story(
        lat = lat.orZero(),
        name = name.orEmpty(),
        description = description.orEmpty(),
        id = id.orEmpty(),
        photoUrl = photoUrl.orEmpty(),
        lon = lon.orZero(),
        createdAt = createdAt.orEmpty()
    )
}