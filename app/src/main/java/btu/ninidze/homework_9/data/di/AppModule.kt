package btu.ninidze.homework_9.data.di

import android.content.Context
import androidx.room.Room
import btu.ninidze.homework_9.data.db.AppDatabase
import btu.ninidze.homework_9.data.db.UserDao
import btu.ninidze.homework_9.data.network.NetworkService
import btu.ninidze.homework_9.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Singleton
    @Provides
    fun provideRetrofit(BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): NetworkService =
        retrofit.create(NetworkService::class.java)

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "user"
    ).build()

    @Singleton
    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()

}