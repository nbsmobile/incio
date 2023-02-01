package com.nbs.kmm.sample.data.story.remote

import com.nbs.kmm.sample.data.story.model.request.GetStoryRequest
import com.nbs.kmm.sample.data.story.model.response.StoryDataResponse
import com.nbs.kmm.sample.utils.ext.isZero
import com.nbs.kmm.sample.utils.ext.toInt
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class StoryApi(private val httpClient: HttpClient): StoryApiClient {

    override suspend fun getAllStories(storyRequest: GetStoryRequest): StoryDataResponse {
        return httpClient.get("stories") {
            if (storyRequest.page.isZero()) {
                parameter("page", storyRequest.page)
            }

            if (storyRequest.size.isZero()) {
                parameter("size", storyRequest.size)
            }

            // TODO: make it optional
            parameter("location", storyRequest.isIncludeLocation.toInt())
        }.body()
    }
}