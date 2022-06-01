package btu.ninidze.homework_9.data.repository.abstraction

import btu.ninidze.homework_9.data.network.User
import btu.ninidze.homework_9.data.network.Resource
import btu.ninidze.homework_9.ui.UserUi

interface IUserRepository {

    suspend fun saveUsers(users: List<User>)

    suspend fun getFirstTenRecord(): List<UserUi>

    suspend fun getUsers(): Resource<List<User>>

    suspend fun leaveFirstTenRecord()

}