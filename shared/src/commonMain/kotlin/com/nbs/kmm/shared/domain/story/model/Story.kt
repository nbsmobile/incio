package com.nbs.kmm.shared.domain.story.model

data class Story(
    val photoUrl: String,
    val description: String,
    val name: String,
    val lon: Double,
    val createdAt: String,
    val id: String,
    val lat: Double
)