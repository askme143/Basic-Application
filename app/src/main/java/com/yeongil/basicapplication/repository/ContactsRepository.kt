package com.yeongil.basicapplication.repository

import com.yeongil.basicapplication.data.Contact
import com.yeongil.basicapplication.dataSource.ContactsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ContactsRepository(private val source: ContactsDataSource, private val dispatcher: CoroutineDispatcher) {
    suspend fun fetch(): List<Contact> {
        return withContext(dispatcher) { source.fetch() }
    }
}

