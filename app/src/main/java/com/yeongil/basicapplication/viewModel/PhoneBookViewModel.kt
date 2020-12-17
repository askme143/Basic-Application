package com.yeongil.basicapplication.viewModel

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yeongil.basicapplication.data.Contact
import com.yeongil.basicapplication.repository.ContactsRepository
import kotlinx.coroutines.*

class PhoneBookViewModel(
    application: Application,
    private val contactsRepository: ContactsRepository
) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>>
        get() = _contacts

    private val _permissions = MutableLiveData<Event<List<String>>>()
    val permissions: LiveData<Event<List<String>>>
        get() = _permissions

    val selectedPosition = MutableLiveData<Int>(-1)

    fun refreshPhoneBook() {
        val notGrantedPermissions = checkPermission(listOf(Manifest.permission.READ_CONTACTS))
        if (notGrantedPermissions.isNotEmpty()) {
            _permissions.value = Event(notGrantedPermissions)
            return
        }

        coroutineScope.launch {
            val contactList = contactsRepository.fetch()
            launch(Dispatchers.Main) { _contacts.value = contactList }
        }
    }

    private fun checkPermission(permissions: List<String>): List<String> {
        val notGrantedList = ArrayList<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notGrantedList.add(permission)
            }
        }

        return notGrantedList.toList()
    }
}

class Event<out T>(private val content: T) {
    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        if (hasBeenHandled) return null
        hasBeenHandled = true
        return content
    }
}