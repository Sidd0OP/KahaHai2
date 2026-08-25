package com.app.trainview.navigation

import androidx.navigation3.runtime.NavKey

sealed interface Destination : NavKey{
    data object Home : Destination
    data object Search : Destination
    data object Map : Destination
}