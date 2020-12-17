package com.yeongil.basicapplication.viewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yeongil.basicapplication.dataSource.ContactsDataSource
import com.yeongil.basicapplication.repository.ContactsRepository
import com.yeongil.basicapplication.viewModel.PhoneBookViewModel
import kotlinx.coroutines.Dispatchers

class PhoneBookViewModelFactory(private val application: Application) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T: ViewModel?> create (modelClass:Class<T>):T{
        return if (modelClass.isAssignableFrom(PhoneBookViewModel::class.java)) {
            val source = ContactsDataSource(application.contentResolver)
            PhoneBookViewModel(application, ContactsRepository(source, Dispatchers.IO)) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}