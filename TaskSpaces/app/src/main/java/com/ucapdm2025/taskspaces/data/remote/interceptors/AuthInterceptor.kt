package com.ucapdm2025.taskspaces.data.remote.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

/**
 * AuthInterceptor is an OkHttp interceptor that adds the Authorization header
 * with a Bearer token to outgoing requests.
 *
 * @param tokenProvider A lambda function that provides the current authentication token.
 */
class AuthInterceptor(
    private val tokenProvider: () -> String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider()
        val request = chain.request().newBuilder().apply {
            if (token.isNotEmpty()) {
                addHeader("Authorization", "Bearer $token")
            }
        }.build()

        return chain.proceed(request)
    }
}