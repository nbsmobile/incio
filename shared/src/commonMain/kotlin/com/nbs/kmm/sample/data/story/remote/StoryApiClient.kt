package com.nbs.kmm.sample.data.story.remote

import com.nbs.kmm.sample.data.story.model.request.GetStoryRequest
import com.nbs.kmm.sample.data.story.model.response.StoryDataResponse
import com.nbs.kmm.sample.data.story.model.response.StoryResponse

interface StoryApiClient {
    suspend fun getAllStories(storyRequest: GetStoryRequest): StoryDataResponse
}