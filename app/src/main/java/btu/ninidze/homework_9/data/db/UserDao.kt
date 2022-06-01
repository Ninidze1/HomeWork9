package btu.ninidze.homework_9.data.db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

    @Query("SELECT * FROM userentity")
    suspend fun getFirstTenRecord(): List<UserEntity>

    @Query("DELETE FROM userentity WHERE id > 10")
    suspend fun leaveFirstTenRecord()

}