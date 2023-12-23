package com.eventbuilding.data
import kotlinx.serialization.Serializable

@Serializable
data class Category (
    val id: Int,
    val title: String,
    val image: String
)