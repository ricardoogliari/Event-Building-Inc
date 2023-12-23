package com.eventbuilding.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eventbuilding.data.Item
import com.eventbuilding.data.SwensonheApi
import com.eventbuilding.logic.FetchCategories
import com.eventbuilding.logic.FetchItens
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class ItensListViewModel(
    val fetchCategories: FetchCategories = FetchCategories(),
    val fetchItens: FetchItens = FetchItens()
) : ViewModel() {

    val state = StateReducerFlow(
        initialState = ItensListState.initial,
        reduceState = ::reduceState,
    )

    private val BASE_URL = "https://swensonhe-dev-challenge.s3.us-west-2.amazonaws.com/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private fun reduceState(
        currentState: ItensListState,
        event: ItensListEvent
    ): ItensListState = when (event) {
        is ItensListEvent.CategoriesScreenStarted -> {
            getCategories()
            currentState
        }
        is ItensListEvent.InternalScreenStarted -> {
            getItens(event.categoryId)
            currentState
        }
        is ItensListEvent.ActionItem -> {
            actionItem(currentState, event.item)
            currentState
        }
        is ItensListEvent.CategoriesLoaded -> {
            currentState.copy(categories = event.categories)
        }
        is ItensListEvent.ItensLoaded -> {
            currentState.copy(itens = event.itens)
        }
    }

    private fun actionItem(currentState: ItensListState, item: Item){
        if (currentState.selectedItens.contains(item).not()){
            currentState.selectedItens = currentState.selectedItens.plusElement(item)
        } else {
            currentState.selectedItens = currentState.selectedItens.minusElement(item)
        }
    }

    private fun getCategories() = viewModelScope.launch  {
        try {
            val listResult = SwensonheApi.retrofitService.getCategories()
            state.handleEvent(ItensListEvent.CategoriesLoaded(listResult))
        } catch (e: IOException) {
            //TODO: tratar exceção profissionalmente
        } catch (e: HttpException) {
            //TODO: tratar exceção profissionalmente
        }
    }

    private fun loadCategoriesByJson() = viewModelScope.launch {
        val categories = fetchCategories()
        state.handleEvent(ItensListEvent.CategoriesLoaded(categories))
    }

    private fun getItens(categoryId: String) = viewModelScope.launch  {
        try {
            val listResult = SwensonheApi.retrofitService.getItens(categoryId)
            state.handleEvent(ItensListEvent.ItensLoaded(listResult))
        } catch (e: IOException) {
            //TODO: tratar exceção profissionalmente
        } catch (e: HttpException) {
            //TODO: tratar exceção profissionalmente
        }
    }

    private fun loadItensByJson() = viewModelScope.launch {
        val itens = fetchItens()
        state.handleEvent(ItensListEvent.ItensLoaded(itens))
    }
}
