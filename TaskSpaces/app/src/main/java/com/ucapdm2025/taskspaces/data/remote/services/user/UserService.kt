package com.ucapdm2025.taskspaces.data.remote.services.user

import com.ucapdm2025.taskspaces.data.remote.responses.BaseResponse
import com.ucapdm2025.taskspaces.data.remote.responses.UserResponse
import retrofit2.http.GET

/** * UserService is an interface that defines the user-related API endpoints.
 * It includes methods for retrieving user information by ID.
 */
interface UserService {
    @GET("/users/:id")
    suspend fun getUserById(id: Int): BaseResponse<UserResponse>
}
