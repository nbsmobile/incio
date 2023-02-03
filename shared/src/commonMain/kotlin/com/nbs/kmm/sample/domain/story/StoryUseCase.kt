package com.nbs.kmm.sample.domain.story

import com.nbs.kmm.sample.domain.story.model.GetStoryParam
import com.nbs.kmm.sample.domain.story.model.Story
import kotlinx.coroutines.flow.Flow

interface StoryUseCase {
    suspend fun getAllStories(storyParam: GetStoryParam): Flow<List<Story>>
    suspend fun uploadStory(file: ByteArray, description: String): Flow<Boolean>
}