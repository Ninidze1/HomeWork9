package btu.ninidze.homework_9.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log.d
import btu.ninidze.homework_9.data.network.Resource
import btu.ninidze.homework_9.data.repository.abstraction.IUserRepository
import btu.ninidze.homework_9.util.Constants.SERVICE_TAG
import btu.ninidze.homework_9.util.Constants.SUCCESS_TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class UserService: Service() {

    @Inject
    lateinit var userRepository: IUserRepository

    init {
        d(SERVICE_TAG, "first service object created")
    }

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        d(SERVICE_TAG, "first service entered onStartCommand()")

        serviceScope.launch {
            d(SERVICE_TAG, "first service entered serviceScope $coroutineContext")

            val users = userRepository.getUsers()

            when(users.status) {
                Resource.Status.SUCCESS -> {
                    users.data?.let {
                        userRepository.saveUsers(it)
                        d(SERVICE_TAG, "users -> $it")

                        val secondIntent = Intent(
                            this@UserService, ReduceUsersService::class.java).apply {
                            putExtra(SUCCESS_TAG, true)
                        }
                        startService(secondIntent)
                    }

                }
                Resource.Status.ERROR -> {
                    d(SERVICE_TAG, "Api call returned with error -> ${users.message}")
                }
            }
            stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        d(SERVICE_TAG, "first service scope finished")
        serviceJob.cancelChildren()
        super.onDestroy()
    }
}