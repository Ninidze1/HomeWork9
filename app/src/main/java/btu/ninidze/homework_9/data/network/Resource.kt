package btu.ninidze.homework_9.data.network

data class Resource<T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null,
) {

    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(Status.SUCCESS, data)
        fun <T> error(message: String): Resource<T> =
            Resource(Status.ERROR, null, message)
    }

    enum class Status { SUCCESS, ERROR }

}