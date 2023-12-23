package com.eventbuilding.logic

import com.eventbuilding.data.Category
import com.eventbuilding.data.Item
import com.eventbuilding.data.categoriesJson
import com.eventbuilding.data.itensJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class FetchItens{

    suspend operator fun invoke(): List<Item> = withContext(Dispatchers.IO) {
        Json.decodeFromString(itensJson)
    }
}