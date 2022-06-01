package btu.ninidze.homework_9.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import btu.ninidze.homework_9.services.UserService
import btu.ninidze.homework_9.data.extensions.getRecyclerViewAdapter
import btu.ninidze.homework_9.databinding.ActivityMainBinding
import btu.ninidze.homework_9.util.Constants.NOTIFY_ACTIVITY
import btu.ninidze.homework_9.util.Constants.SUCCESS_TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        startService()
        initBroadReceiver()
        initRecyclerViewAdapter()
        subscribeToObservers()
    }

    private fun subscribeToObservers() = with(binding) {
        viewModel.users.observe(this@MainActivity) { users ->
            rwUsers.getRecyclerViewAdapter<UserRecyclerViewAdapter>().initList(users)
            progressBar.visibility = View.GONE
        }
    }

    private fun initRecyclerViewAdapter() = with(binding) {
        rwUsers.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = UserRecyclerViewAdapter()
        }
    }

    private fun startService() {
        val intent = Intent(this, UserService::class.java)
        startService(intent)
    }

    private fun initBroadReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(serviceCallBackReceiver, IntentFilter(NOTIFY_ACTIVITY))
    }

    private val serviceCallBackReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val bundle = intent.extras ?: return
            if (!bundle.containsKey(SUCCESS_TAG)) {
                return
            }
            val isSuccessful = bundle.getBoolean(SUCCESS_TAG)
            if (isSuccessful) {
                viewModel.getUsers()
            }
        }
    }

}