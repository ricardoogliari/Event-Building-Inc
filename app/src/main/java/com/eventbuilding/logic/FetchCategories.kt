package com.eventbuilding.logic

import com.eventbuilding.data.Category
import com.eventbuilding.data.categoriesJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class FetchCategories {

    suspend operator fun invoke(): List<Category> = withContext(Dispatchers.IO) {
        Json.decodeFromString(categoriesJson)
    }
}