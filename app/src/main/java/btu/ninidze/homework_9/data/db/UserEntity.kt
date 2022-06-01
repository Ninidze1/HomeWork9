package btu.ninidze.homework_9.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import btu.ninidze.homework_9.data.network.User
import btu.ninidze.homework_9.ui.UserUi

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "firstName") val firstName: String,
    @ColumnInfo(name = "lastName") val lastName: String,
    @ColumnInfo(name = "avatar") val avatar: String,
) {
    fun toPresentation(): UserUi {
        return UserUi(
            id = id,
            email = email,
            firstName = firstName,
            lastName = lastName,
            avatar = avatar,
        )
    }

    companion object {
        fun fromDto(type: User): UserEntity {
            return UserEntity(
                id = type.id ?: 0,
                email = type.email ?: "",
                firstName = type.firstName ?: "",
                lastName = type.lastName ?: "",
                avatar = type.avatar ?: "",
            )
        }
    }
}