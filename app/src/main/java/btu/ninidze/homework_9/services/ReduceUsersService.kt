package btu.ninidze.homework_9.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log.d
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import btu.ninidze.homework_9.data.repository.abstraction.IUserRepository
import btu.ninidze.homework_9.util.Constants.NOTIFY_ACTIVITY
import btu.ninidze.homework_9.util.Constants.SERVICE_TAG
import btu.ninidze.homework_9.util.Constants.SUCCESS_TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class ReduceUsersService: Service() {

    @Inject
    lateinit var userRepository: IUserRepository

    init {
        d(SERVICE_TAG, "second service object created")
    }

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        d(SERVICE_TAG, "second service entered onStartCommand()")
        serviceScope.launch {
            d(SERVICE_TAG, "second service entered serviceScope $coroutineContext")

            val hasReceived = intent?.getBooleanExtra(SUCCESS_TAG, false)
            if (hasReceived == true) {
                d(SERVICE_TAG, "second service proceeded successfully")
                userRepository.leaveFirstTenRecord()
                val secondIntent = Intent(NOTIFY_ACTIVITY)
                secondIntent.putExtra(SUCCESS_TAG, true)
                LocalBroadcastManager.getInstance(this@ReduceUsersService).sendBroadcast(secondIntent)
            }
            stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        d(SERVICE_TAG, "second service scope finished")
        serviceJob.cancelChildren()
        super.onDestroy()
    }

}
