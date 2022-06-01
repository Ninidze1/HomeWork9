package btu.ninidze.homework_9.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import btu.ninidze.homework_9.data.network.Resource
import btu.ninidze.homework_9.data.repository.abstraction.IUserRepository
import btu.ninidze.homework_9.data.repository.implementation.UserRepository
import btu.ninidze.homework_9.ui.UserUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userRepository: IUserRepository): ViewModel() {

    private var _users = MutableLiveData<List<UserUi>>()
    val users: LiveData<List<UserUi>> = _users

    fun getUsers() {
        viewModelScope.launch {
            _users.postValue(userRepository.getFirstTenRecord())
        }
    }
}