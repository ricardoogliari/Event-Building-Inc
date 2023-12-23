package com.eventbuilding.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SavedScreen(minValue: String, maxValue: String){
    Column {
        Text(text = "Event saved!")
        Text(text = "$minValue - $maxValue")
    }
}