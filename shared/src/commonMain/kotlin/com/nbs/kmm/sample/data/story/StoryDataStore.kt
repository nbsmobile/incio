package com.nbs.kmm.sample.data.story

import com.nbs.kmm.sample.data.story.model.request.GetStoryRequest
import com.nbs.kmm.sample.data.story.model.response.StoryDataResponse
import com.nbs.kmm.sample.data.story.remote.StoryApiClient

class StoryDataStore(private val storyApiClient: StoryApiClient): StoryRepository {
    override suspend fun getAllStories(storyRequest: GetStoryRequest): StoryDataResponse {
        return storyApiClient.getAllStories(storyRequest)
    }
}