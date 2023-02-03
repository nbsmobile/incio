package com.nbs.kmm.sample.data.story

import com.nbs.kmm.sample.data.story.model.request.GetStoryRequest
import com.nbs.kmm.sample.data.story.model.response.StoryDataResponse
import com.nbs.kmm.sample.data.story.model.response.UploadStoryResponse

interface StoryRepository {
    suspend fun getAllStories(storyRequest: GetStoryRequest): StoryDataResponse
    suspend fun uplaodStory(file: ByteArray, description: String): UploadStoryResponse
}