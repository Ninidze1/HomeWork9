package btu.ninidze.homework_9.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("users")
    suspend fun getUsers(@Query("page") page: Int): Response<ResponseItems<List<User>>>
}