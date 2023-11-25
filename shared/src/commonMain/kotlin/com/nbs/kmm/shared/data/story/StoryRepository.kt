package com.nbs.kmm.shared.data.story

import com.nbs.kmm.shared.data.story.model.request.GetStoryRequest
import com.nbs.kmm.shared.data.story.model.response.StoryDataResponse
import com.nbs.kmm.shared.data.story.model.response.UploadStoryResponse

interface StoryRepository {
    suspend fun getAllStories(storyRequest: GetStoryRequest): StoryDataResponse
    suspend fun uplaodStory(file: ByteArray, description: String): UploadStoryResponse
}