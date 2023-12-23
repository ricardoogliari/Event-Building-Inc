package com.eventbuilding.state

import com.eventbuilding.data.Category
import com.eventbuilding.data.Item

data class ItensListState(
    var itens: List<Item>,
    var selectedItens: List<Item>,
    val categories: List<Category>,
    var category: Category?
) {

    companion object {
        val initial = ItensListState(
            itens = emptyList(),
            selectedItens = emptyList(),
            categories = emptyList(),
            category = null
        )
    }
}