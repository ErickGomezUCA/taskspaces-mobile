package com.ucapdm2025.taskspaces.data.remote.services

import com.ucapdm2025.taskspaces.data.remote.responses.BaseResponse
import com.ucapdm2025.taskspaces.data.remote.responses.BookmarkResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * BookmarkService is an interface that defines the API endpoints for managing bookmarks.
 * It includes methods for creating, retrieving, and deleting bookmarks related to tasks.
 */
interface BookmarkService {
    //    For these 3 GETs it is not necessary to pass the user id as parameter, because that info
//    comes from the Auth token
    @GET("bookmarks/")
    suspend fun getUserBookmarks(): BaseResponse<List<BookmarkResponse>>

    @GET("bookmarks/t/{taskId}")
    suspend fun getBookmarkByTaskId(@Path("taskId") taskId: Int): BaseResponse<BookmarkResponse>

    //    POST and DELETE do not use a body, these endpoints are handled just by path params
    @POST("bookmarks/t/{taskId}")
    suspend fun createBookmark(@Path("taskId") taskId: Int): BaseResponse<BookmarkResponse>

//    This service does not have a PUT method

    @DELETE("bookmarks/t/{taskId}")
    suspend fun deleteBookmark(@Path("taskId") taskId: Int): BaseResponse<BookmarkResponse>
}