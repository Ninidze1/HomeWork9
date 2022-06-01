package btu.ninidze.homework_9.data.network

import btu.ninidze.homework_9.ui.UserUi
import com.google.gson.annotations.SerializedName

data class User(
    val id: Long?,
    val email: String?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    val avatar: String?
)

data class ResponseItems<T> (
    val page: Int?,
    @SerializedName("per_page")
    val perPage: Int,
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    val data: T?,
)