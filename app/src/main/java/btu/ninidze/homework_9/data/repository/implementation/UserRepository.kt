package btu.ninidze.homework_9.data.repository.implementation

import btu.ninidze.homework_9.data.db.UserDao
import btu.ninidze.homework_9.data.db.UserEntity
import btu.ninidze.homework_9.data.network.User
import btu.ninidze.homework_9.data.network.NetworkService
import btu.ninidze.homework_9.data.network.Resource
import btu.ninidze.homework_9.data.repository.abstraction.IUserRepository
import btu.ninidze.homework_9.ui.UserUi
import btu.ninidze.homework_9.util.Constants.PAGES_2
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val db: UserDao,
    private val apiService: NetworkService
    ) : IUserRepository {


    override suspend fun saveUsers(users: List<User>) {
        db.insertAll(users.map { UserEntity.fromDto(it) })
    }

    override suspend fun getFirstTenRecord(): List<UserUi> =
        db.getFirstTenRecord().map { it.toPresentation() }

    override suspend fun getUsers(): Resource<List<User>> {
        return try {
            val response = apiService.getUsers(PAGES_2)
            return if (response.isSuccessful) {
                Resource.success(response.body()?.data!!)
            } else {
                Resource.error(response.message())
            }
        } catch (e: Exception) {
            Resource.error(e.message.toString())
        }
    }

    override suspend fun leaveFirstTenRecord() {
        db.leaveFirstTenRecord()
    }

}