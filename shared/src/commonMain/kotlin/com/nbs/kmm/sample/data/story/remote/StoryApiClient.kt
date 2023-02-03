package com.nbs.kmm.sample.data.story.remote

import com.nbs.kmm.sample.data.story.model.request.GetStoryRequest
import com.nbs.kmm.sample.data.story.model.response.StoryDataResponse
import com.nbs.kmm.sample.data.story.model.response.UploadStoryResponse

interface StoryApiClient {
    suspend fun getAllStories(storyRequest: GetStoryRequest): StoryDataResponse
    suspend fun uploadStory(file: ByteArray, description: String): UploadStoryResponse
}