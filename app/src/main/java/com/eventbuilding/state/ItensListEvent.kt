package com.eventbuilding.state

import com.eventbuilding.data.Category
import com.eventbuilding.data.Item

sealed class ItensListEvent {
    object CategoriesScreenStarted : ItensListEvent()
    data class InternalScreenStarted(val categoryId: String) : ItensListEvent()
    data class ActionItem(val item: Item) : ItensListEvent()
    data class CategoriesLoaded(val categories: List<Category>) : ItensListEvent()
    data class ItensLoaded(val itens: List<Item>) : ItensListEvent()
}