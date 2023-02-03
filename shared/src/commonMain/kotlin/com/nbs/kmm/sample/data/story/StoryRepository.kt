package com.nbs.kmm.sample.data.story

import com.nbs.kmm.sample.data.story.model.request.GetStoryRequest
import com.nbs.kmm.sample.data.story.model.response.StoryDataResponse

interface StoryRepository {
    suspend fun getAllStories(storyRequest: GetStoryRequest): StoryDataResponse
}