package com.eventbuilding.navigation

sealed class Screens(val route: String) {
    object Main: Screens("main_screen")
    object Internal: Screens("internal_screen/{categoryName}/{categoryId}"){
        fun createRoute(categoryName: String, categoryId: Int) = "internal_screen/$categoryName/$categoryId"
    }
    object Saved: Screens("savedScreen/{min}/{max}"){
        fun createRoute(min: String, max: String) = "savedScreen/$min/$max"
    }

}
