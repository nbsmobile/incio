package com.nbs.kmm.sample.domain.story

import com.nbs.kmm.sample.data.story.StoryRepository
import com.nbs.kmm.sample.domain.base.execute
import com.nbs.kmm.sample.domain.story.mapper.toDomain
import com.nbs.kmm.sample.domain.story.mapper.toRequest
import com.nbs.kmm.sample.domain.story.model.GetStoryParam
import com.nbs.kmm.sample.domain.story.model.Story
import kotlinx.coroutines.flow.Flow

class StoryInteractor(private val storyRepository: StoryRepository): StoryUseCase {

    override suspend fun getAllStories(storyParam: GetStoryParam): Flow<List<Story>> {
        return execute {
            storyRepository.getAllStories(storyParam.toRequest()).stories
                .map { it.toDomain() }
        }
    }
}