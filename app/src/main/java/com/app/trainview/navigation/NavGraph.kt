package com.app.trainview.navigation

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.EntryProviderScope
import com.app.trainview.features.home.HomeScreen
import com.app.trainview.features.map.MapScreen
import com.app.trainview.features.search.SearchScreen



fun EntryProviderScope<Any>.navEntry(
    backStack: SnapshotStateList<Any>,
) {
    entry<Destination.Home> {
        HomeScreen(goToMap = {
            backStack.add(Destination.Map)
        })
    }

    entry<Destination.Search> {
        SearchScreen()
    }

    entry<Destination.Map> {
        MapScreen(goToHome = {
            backStack.removeAt(backStack.lastIndex)
        })
    }
}
