package com.ucapdm2025.taskspaces.helpers

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.ucapdm2025.taskspaces.data.model.SearchModel

/**
 * SearchHolder is a singleton object that holds the current search query and results.
 * It is used to maintain the state of the search functionality across the application.
 *
 * @property searchQuery The current search query entered by the user.
 * @property results The list of search results corresponding to the current search query.
 */
object SearchHolder {
    var searchQuery: MutableState<String> = mutableStateOf("")
    var results: MutableState<SearchModel?> = mutableStateOf(null)
}