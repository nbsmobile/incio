package com.nbs.kmm.shared.data.story

import com.nbs.kmm.shared.data.story.model.request.GetStoryRequest
import com.nbs.kmm.shared.data.story.model.response.StoryDataResponse
import com.nbs.kmm.shared.data.story.model.response.UploadStoryResponse
import com.nbs.kmm.shared.data.story.remote.StoryApiClient

class StoryDataStore(private val storyApiClient: StoryApiClient): StoryRepository {
    override suspend fun getAllStories(storyRequest: GetStoryRequest): StoryDataResponse {
        return storyApiClient.getAllStories(storyRequest)
    }

    override suspend fun uplaodStory(file: ByteArray, description: String): UploadStoryResponse {
        return storyApiClient.uploadStory(file, description)
    }
}