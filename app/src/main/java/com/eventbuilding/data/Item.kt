package com.eventbuilding.data
import kotlinx.serialization.Serializable

@Serializable
data class Item (
    val id: Int,
    val title: String,
    val minBudget: Double,
    val maxBudget: Double,
    val avgBudget: Double,
    val image: String
)